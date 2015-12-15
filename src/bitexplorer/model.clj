(ns bitexplorer.model
  (:use [hiccup core page] )
  (:require [clj-http.client :as http])
  ;(:require [clojure.string :as string])
  (:require [clojure.edn :as edn])
  (:require [clojure.data.json :as json])
  
  )

(defn get-search-result [search-string]
  (->(http/get (str "http://localhost:8080/search/" search-string )) :body json/read-str )
  )

(defn get-bestblock []
  (->(http/get (str "http://localhost:8080/bestBlock/" )) :body json/read-str )
  )

 (defn get-txlist-data [blockid]
  (->(http/get (str "http://localhost:8080/txs/" blockid)) :body json/read-str)
  )
 
 (defn get-txitem-data [txid]
  (->(http/get (str "http://localhost:8080/tx/" txid)) :body json/read-str)
  )
 
  (defn get-blocklist-data [ topblockid]
    (let [blockid (or  topblockid "top")]
      (json/read-str(:body (http/get (str "http://localhost:8080/blocks/" blockid)  ))))
  
  )
  
 (defn get-block-data [blockid]
   (->(http/get (str "http://localhost:8080/block/" blockid)) :body json/read-str)
   )
  
 
  (defn get-account-data [accid offset]
  (->(http/get (str "http://localhost:8080/account/" accid "/" offset)) :body json/read-str)
  )
 
   (defn get-tx-data [txid]
  (->(http/get (str "http://localhost:8080/tx/" txid)) :body json/read-str)
  )
   
  (defn block [blockid]
  (get-block-data blockid)
  )
