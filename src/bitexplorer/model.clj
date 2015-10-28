(ns bitexplorer.model
  (:use [hiccup core page])
  (:require [clj-http.client :as http])
  ;(:require [clojure.string :as string])
  (:require [clojure.edn :as edn])
  
  )

(defn get-search-result [search-string]
  (->(http/get (str "http://localhost:8080/search/" search-string )) :body edn/read-string )
  )

(defn get-bestblock []
  (->(http/get (str "http://localhost:8080/bestBlock/" )) :body edn/read-string )
  )

 (defn get-txlist-data [blockid]
  (->(http/get (str "http://localhost:8080/txs/" blockid)) :body edn/read-string)
  )
 
 (defn get-txitem-data [txid]
  (->(http/get (str "http://localhost:8080/tx/" txid)) :body edn/read-string)
  )
 
  (defn get-blocklist-data [ topblockid]
    (let [blockid (or  topblockid "top")]
      (edn/read-string(:body (http/get (str "http://localhost:8080/blocks/" blockid)  ))))
  
  )
  
 (defn get-block-data [blockid]
   (->(http/get (str "http://localhost:8080/block/" blockid)) :body edn/read-string)
   )
  
 
  (defn get-account-data [accid offset]
  (->(http/get (str "http://localhost:8080/account/" accid "/" offset)) :body edn/read-string)
  )
 
   (defn get-tx-data [txid]
  (->(http/get (str "http://localhost:8080/tx/" txid)) :body edn/read-string)
  )
   
  (defn block [blockid]
  (get-block-data blockid)
  )
