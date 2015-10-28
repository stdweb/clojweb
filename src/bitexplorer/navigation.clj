(ns bitexplorer.navigation
  (:use [hiccup core page] 
        bitexplorer.model 
        bitexplorer.utils
        bitexplorer.html-utils
        bitexplorer.sharedview [hiccup form ])
  
  (:require [clj-http.client :as http])
  (:require [clojure.string :as string])  
  )


(defn navig-form [topblockid]
[:div {:id "navigate-block"  :style "float:left"}
 (form-to  {:enctype "multipart/form-data"} [:post "/goto"]
        (label "top-block" "Search Block")
        ;(text-field :blockid (get-bestblock))
        (text-field :blockid topblockid)
        (submit-button {:name "btngotoblock" :value "Go to" :class "btn"} "Goto")
        (submit-button {:name "btngotoblock" :value "Top" :class "btn" } "Goto")
        (submit-button {:name "btngotoblock" :value " < " :class "btn" } "Goto")
        (submit-button {:name "btngotoblock" :value " > " :class "btn" } "Goto")
        
        )]

)

(defn navig-acc-form [acc-hash offset page-count]
[:div {:id "navigate-block"  :style "float:left"}
 (form-to  {:enctype "multipart/form-data"} [:post "/accpage"]
        (label "Account" "Search Account")
        (text-field {:style "width:400px"} :acc acc-hash)
        (submit-button {:name "btngoto" :value "Go to" :class "btn"} "Goto")
        (label "page" "   page  ")
        (text-field {:style "width:30px"} :offset offset)
        (label "of" (str "  of  " ))
        (text-field {:style "width:30px;border-style:none" :readonly "readonly"} :page page-count)
        
        
        
        
        (submit-button {:name "btngoto" :value "<<" :class "btn" } "Goto")
        (submit-button {:name "btngoto" :value " < " :class "btn" } "Goto")
        (submit-button {:name "btngoto" :value " > " :class "btn" } "Goto")
        (submit-button {:name "btngoto" :value ">>" :class "btn" } "Goto")
        )]

)
