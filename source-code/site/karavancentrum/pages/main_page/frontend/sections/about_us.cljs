
(ns site.karavancentrum.pages.main-page.frontend.sections.about-us
    (:require [app.contents.frontend.api :as contents]
              [mid-fruits.css            :as css]
              [re-frame.api              :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn about-us
  []
  (let [about-us @(r/subscribe [:db/get-item [:site :contents :about-us]])]
       [:section [:div#about-us [:div.kc-section-title "Magunkról"]
                                [contents/content-preview {:item-id (:content/id about-us)}]]]))

(defn view
  []
  [about-us])
