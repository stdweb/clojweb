(ns bitexplorer.utils)

(def select-values (comp reverse vals select-keys))


(defn parse-int [s]
   
  (try (Integer. (re-find  #"\d+" s ))
    (catch Exception e nil))   )

(defn map-tag [tag xs]
  (map (fn [x] [tag x]) xs))

(defn floor [amount]
  (let [
        ;s (subs (clojure.string/replace amount "," "") 0 (max 0 (.indexOf amount ".")))
        ;s (clojure.string/replace amount "," "")
        ret (first (clojure.string/split amount #"\."))
        ]
    ret
    ;(if (= ret  "") 0 ret)
    ))

(defn fract [amount]
  (let [
        ;s (subs (clojure.string/replace amount "," "") 0 (max 0 (.indexOf amount ".")))
        s (str  (clojure.string/replace amount "," "") ".")
        ret (second (clojure.string/split s #"\."))
        ]
   (if (> (count ret ) 0 )   (str "." ret) ret)
    ))
  