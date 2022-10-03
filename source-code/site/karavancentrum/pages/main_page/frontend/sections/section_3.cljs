
(ns site.karavancentrum.pages.main-page.frontend.sections.section-3
  (:require
    [re-frame.api :as r]
    [utils.api :refer [html->hiccup]]))


;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn content []
  (let [data @(r/subscribe [:site/get [:contents :contacts-data-information :content/body]])]
    [:div#contacts
      (html->hiccup data)]))

(defn section-3 []
  [:<>
   [:section
    [content]
    [:section#section-3--background]]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [section-3])
