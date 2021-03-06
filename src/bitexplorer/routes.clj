(ns bitexplorer.routes
  (:use compojure.core
        bitexplorer.index
        bitexplorer.blockview
        bitexplorer.acc-view
        bitexplorer.tx-view
        bitexplorer.model
        bitexplorer.utils
        bitexplorer.sharedview 
        [bitexplorer.genesisview :as genesis]
        [ring.middleware.logger :as logger]
        
        
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [ring.util.response :as resp]
            [clj-http.client :as http]
            ))

(defn logentry [handler & [base-url]]
   ;handler
   (fn [request]
     (let [
           t1  (java.lang.System/currentTimeMillis)
           resp (handler request)
           ]
      
     ;(http/get (str "http://localhost:8080/log/" (clojure.string/replace (:remote-addr request ) "." "_") ))
     ;(str(- java.lang.System/currentTimeMillis t1))
      (http/get (str "http://localhost:8080/log?uri="  ( :uri request) "&ip=" (:remote-addr request ) 
                     "&duration=" (str (- (java.lang.System/currentTimeMillis) t1))  ))
      resp
     )))

(defn blockid-from-request [ req ]
  (let [
        button ( (req :multipart-params) "btngotoblock")
        blockidStr ((req  :multipart-params) "blockid")
        blockNo (or ( parse-int blockidStr) (get-bestblock))
        
        ;blockNo (if (number? blockNoParse) blockNoParse (get-bestblock))
        ;(if (number? blockNoParse) ())
        ]
    (
      case button
      "Top" (get-bestblock)
      "Go to" (min blockNo (get-bestblock))
      " < "  (max(- blockNo 40 ) 0)
      " > "  (min (+ blockNo 40 ) (get-bestblock))
      )))

(defn acc-offset-from-request [ req ]
  (let [
        button ( (req :multipart-params) "btngoto")
        acc ((req  :multipart-params) "acc")
        offsetStr ((req  :multipart-params) "offset")
        offset (or ( parse-int offsetStr) 1)
        offset-1 (- offset 1)
        page-count (or ( parse-int ((req  :multipart-params) "page")) 1)
        ;blockNo (if (number? blockNoParse) blockNoParse (get-bestblock))
        ;(if (number? blockNoParse) ())
        ]
    (
      case button
      "<<" (str acc "/" "1")
      ">>" (str acc "/" page-count)
      "Go to" (str acc "/" offset)
      " < "  (str acc "/" (if (zero? offset-1) 1 offset-1))
      " > "  (str acc "/" (min (+ 1 offset) page-count))
      )))

(defn search-redirect [request]
  (let [
        search-string ((request :multipart-params) "search")
        search-result (get-search-result search-string)
        ]
    
    (case (search-result "resulttype")
      "address" (resp/redirect (str "/account/" (search-result "address")))
      "block" (resp/redirect (str "/block/" (search-result "blocknumber")))
      "tx" (resp/redirect (str "/tx/" (search-result "tx")))
      
      (htmlpage [:body [:p (str "" search-string  " not found") ]])
      )
    
    ))

(defroutes main-routes
  (GET "/" [] (resp/redirect (str "/index/top"  )))
  
  (GET "/req/:p" request (str request))
  ;(GET "/req" request (http/get (str "http://localhost:8080/log/" (clojure.string/replace (:params request ) "." "_")  ) ))
  
  (GET "/block/:blockid" [blockid] (try (block-html blockid) (catch Exception e (htmlpage [:body [:p (str "Block " blockid " not found") ]]))) )
  (GET "/tx/:txid" [txid] (try (tx-html txid) (catch Exception e (htmlpage [:body [:p (str "Transaction " txid " not found") ]]))))
  (GET "/account/Genesis" [] (genesis/view) )
  (GET "/account/genesis" [] (genesis/view) )
  
  (GET "/account/:accid" [accid] (acc-html accid 1 ))
  (GET "/account/:accid/:offset" [accid offset] (acc-html accid offset))
  
  (GET "/contract/:contrid" [contrid] (str "<h1>contract requested " contrid "</h1>"))
  
  (GET "/index" [] (resp/redirect (str "/index/top"  )))
  (GET "/index/" [] (resp/redirect (str "/index/top"  )))
  
  (GET "/index/:topblockid" [topblockid] (blocklist topblockid))
  ;(GET "/txs" [] (txlist))
  (GET "/accounts" [] (str "<h1>account list </h1>"))
  (GET "/contracts" [] (str "<h1>contract list </h1>"))
  ;(POST "/goto"  [:as request] (blocklist ( (request  :multipart-params) "blockid")))
  (POST "/goto"  [:as request] (resp/redirect (str "/index/" (blockid-from-request request) )))
  (POST "/accpage"  [:as request] (resp/redirect (str "/account/" (acc-offset-from-request request) )))
  (POST "/search"  [:as request] (search-redirect request))
  ;(POST "/accpage"  [:as request] (str request))
  
  ;(reqeust  :multipart-params)
  
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
     ;(wrap-base-url)
      (logentry)
      ;(logger/wrap-with-logger)
      
      )) 

