
(ns site.frontend.modules.breadcrumbs
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-components.api :as components]))


;; -----------------------------------------------------------------------------
;; ---- Utils ----

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn crumb [text]
  [:a {:key  text
       :href (str "/" text)}
   text])

(defn asd [data]
  (cond
    (vector? data) (map crumb data)
    (string? data) (asd (clojure.string/split data #"/"))
    :else (println "Cant create breadcrumbs with this: " data)))

(defn dsa [data]
  (map (fn [a]
         ^{:key (random-uuid)} [:<> a])
       data))

(defn breadcrumbs [crumbs-data]
  [:div#breadcrumbs--container
   [:div#breadcrumbs
    (if (= 1 (count crumbs-data))
      (asd (first crumbs-data))
      (dsa crumbs-data))]])


(defn view [& data]
  [breadcrumbs data])

;; ---- Components ----
;; -----------------------------------------------------------------------------
