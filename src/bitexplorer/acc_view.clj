
(ns bitexplorer.acc-view
  (:use [hiccup core page] 
        bitexplorer.model 
        bitexplorer.html-utils
        bitexplorer.sharedview 
        bitexplorer.tx-view
        bitexplorer.utils
        bitexplorer.navigation
        )
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])
  
  )

(defn get-acc-item-href [item-data]
  (let [
        account ( item-data "addr_str")
        offset ( item-data "offsetAddr_str")
        tx ( item-data "hash_str")
        block (item-data "blockId")
       
        
        account-href (hash2href "account" account  )
        offset-href (hash2href "account" offset  )
        tx-href  (hash2href "tx" tx)
        block-href    [:a {:href (str "/block/" block)} block]
        
        ]
     (assoc item-data "ADDRESS" account-href "TX" tx-href "BLOCK" block-href "OFFSETACCOUNT" offset-href)
    ))

(defn getAmountStyle [amount] 
  (let [
        isneg (= "-" (subs (str amount " ") 0 1))
        style {:style (if isneg "text-align:right;color:red" "") }
        ]
    style
    ;[:td style amount]
                                 
    ) )

(defn get-acc-item-html [item-data]
  (let [
        data (get-acc-item-href item-data)
        ;depth (parse-int (data "depth"))
        depth (data "depth")
        gasUsed (data "gasUsed")
        ]
    [:tr ;(map-tag :td (select-values data [ "BLOCK" "BLOCKTIMESTAMP" "TX" "OFFSETACCOUNT"  "AMOUNT" "FEE" "ENTRYTYPE"   ]))
     
     [:td (merge-style (entryresult-style data "") {:style "rowspan:2"}) (data "BLOCK")]
     [:td (data "blockDateTime")]
     
     [:td (if (zero? (or depth 0))  ( data "TX") "")]
     ;
     [:td (td-style data "entryType") (data "entryType")]
     
     [:td (td-style data "offset" ) (data "OFFSETACCOUNT")]
     
     [:td (td-style data "floor") (floor (data "received_str"))]
     [:td (td-style data "fract") (fract (data "received_str"))]
     [:td (td-style data "floor") (floor (data "sent_str"))]
     [:td (td-style data "fract") (fract (data "sent_str"))]
    ; [:td (data "DEPTH")]
     ;[:td (data "FEE")]
     ;[:td (data "GROSSAMOUNT")]
     [:td (if (zero? gasUsed) "" gasUsed)]
     [:td (td-style data "floor") (floor(data "balance_str"))]
     [:td (td-style data "fract") (fract(data "balance_str"))]
     ;[:td depth]
     ])
  )

(defn create-acc-table [data accid offset attribs]
  (list
;tx ,address ,amount ,block ,blocktimestamp,depth ,gasused ,fee ,entryType   
   [:div.blocklist  
    [:table
     [:tr 
      [:th "Block"] 
      [:th  "Date" ] 
      [:th "Tx"] 
      [:th "Entry type"] 
      [:th "Offset Acc"] 
      [:th {:class "th-colspan" :colspan "2"} "Received (Finney)"] 
      [:th {:class "th-colspan" :colspan "2"} "Sent (Finney)"]
     
       ;[:th "Fee"] 
      ;[:th "GrossAmount"]
      ;[:th {:class "th-colspan" :colspan "2"} "Balance"]
      [:th "Gas used"]
      [:th {:class "th-colspan" :colspan "2"} "Balance"]
       ;[:th "Depth"]
      
      ]
                          (map #(get-acc-item-html %) data)    
                          ]]))


(defn acc-html [accid offset] 
  
  (let [
        data (get-account-data accid offset)
        entries-count (data "entries_count")
        page-count (data "page_count")
        entries (data "entries")
        balance (data "balance")
        address-type (data "addresstype")
        first-block (data "firstblock")
        last-block (data "lastblock")
        ]
    (htmlpage 
     (list (navig-acc-form accid offset page-count)
     [:head ] [:br " "]
     [:body 
      ;[:h2 "Account# " accid]
      ;[:p "Entries count " entries-count]
      [:table {:class "blockheader"}
       [:tr [:td {:class "blockheader-key"} address-type] [:td {:class "blockheader-key"} accid]]
       [:tr [:td {:class "blockheader-key"} "Entries count"] [:td {:class "blockheader-key"} entries-count]]
       [:tr [:td {:class "blockheader-key"} "Balance"] [:td {:class "blockheader-key"} balance]]
       [:tr [:td {:class "blockheader-key"} "First Block"] [:td {:class "blockheader-key"} first-block]]
       [:tr [:td {:class "blockheader-key"} "Last Block"] [:td {:class "blockheader-key"} last-block]]
       ]
      ;(map2htmltable (get-block-data blockid) {:class "blockheader"})
      [:hr]
      (create-acc-table entries accid offset {:class "blockdetails"})
      ]))))

