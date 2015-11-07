(ns bitexplorer.sharedview
  (:use [hiccup core page] bitexplorer.model bitexplorer.utils
       [hiccup form ]
        )

  )

;(def navig-pane
;  [:div {:style "clear:both"}  
;   [:menu 
;    [:button "<< first"]
;    [:button "< prev"]
;    [:text "1 of 1000"]
;    [:button "next >"]
;    [:button "last >>"]
;    ]]
;  )

(defn search-form []
[:div {:id "search-form"  }
 (form-to  {:enctype "multipart/form-data"} [:post "/search"]
        (label {:id "search-label"} "label" "  Search  ");by block number, hash or address
        (text-field {:style "width:450px"} :search nil) ""
        (submit-button {:name "btngotoblock" :value "Go" :class "btn"} "Goto")
        )]
)
       ; [:br]
        

(def sharedview 
  (list 
    [:title "λΞ - Ethereum block explorer"]
    [:div.menu 
     
     [:div#css-menu [:ul   
        [:li [:a {:href "/index/top"} [:span.text-top "BitλΞedger " ] [:span.text-bottom "Ethereum Block explorer"]]]   
        
        [:li (search-form)]
        ;[:li [:a  {:href "mailto://bitledger@bitledger.com"} [:span.text-top "Feedback"] [:span.text-bottom "bitledger@bitledger.com"]]]
      ;  [:li [:a {:href "/index/top"} [:span.text-top "Block list" ] [:span.text-bottom "last blocks"]]] 
      ;  [:li [:a  [:span.text-top "Contracts"] [:span.text-bottom "all contract list"]]]
      ;  [:li [:a  [:span.text-top "Accounts"] [:span.text-bottom "all contract list"]]]
      
        ]]
     [:br {:style "clear:both"} "" ]
     ;navig-pane
     ;(navig-form)
     ;[:br " "]
     ]
    
    ;[:hr {:style "clear:both"}]
        )
  )

(defn htmlpage [body]
  (html5 
    [:head   sharedview      (include-css "/css/style.css")]
    body
    )
  )