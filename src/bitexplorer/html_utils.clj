(ns bitexplorer.html-utils
   (:use [hiccup core page] bitexplorer.utils)
  
  )

(defn row [k v]
  [:tr [:td k] [:td v]]
  
  )

(defn block-header-row [k v]
  [:tr [:td {:class "blockheader-key"} k] [:td {:class "blockheader-val"} v]]
  
  )

(defn hash2href [path hash & [attribs]  ]
  (let [
        hash1 (or hash "")
        attributes (merge {:title hash1} (or attribs nil) {:href (str "/" path "/" hash1)} )
        label (str (subs hash1 0 (min (count hash) 10)) (if (> (count hash1) 10) "..." "" ))
        ]
    
    (if (or (< (count hash1) 5 ) (nil? hash)) "" [:a  attributes label ])
     
    ))


(defn map2htmltable [m attribs]

  (into [:table attribs
         [:tr  (map-tag :th [ "Key" "Value "])]
         ]
        (map #(row % ( m %)) (keys m)))  
)

(defn internalCall-style [data]
  (let [
        ;depth (parse-int (data "DEPTH"))
        depth (data "depth")
        style {:style (if (pos? (or depth 0)) (str "text-indent:" (* 0.5 depth) "em") "") }
        ]
    style
    ;[:td style amount]
    )
  )

(defn merge-style [style1 style2]
  (let [styletag (str (style1 :style) ";" (style2 :style))]
    (merge style1 style2 {:style styletag})
    
    )
  )

(defn td-style2 [data n]
  (case n
    "sender"   (internalCall-style data )
    "receiver" (internalCall-style data )
    "offset" (internalCall-style data )
    "floor" {:class "floor-part" }
    "floor-no-red" {:class "floor-part" }
    "fract" {:class "fract-part"}
    "fract-no-red" {:class "fract-part"}
    ;"entryType" {:class  "txtype" }
    "entryType" (internalCall-style data )
    
    {}
    )
  )

(defn entryresult-style [data n]
  (let [ style 
        (case (data "entryResult")
          "Failed" {:style "color:red;text-decoration: line-through;"}
          "Ok" {}
          "Total" {:style "font-weight:bold"}
          {}
          )
        ]
    style
    ;(merge-style (td-style2 data n) style)
    )
  )
(defn td-style [data n]
  ( case n
    "floor-no-red" (td-style2 data n )
    "fract-no-red" (td-style2 data n )
    (merge-style (td-style2 data n ) (entryresult-style data n))
    ))
  
  
 
  
  
  
