
(ns site.karavancentrum.pages.main-page.frontend.sections.about-us
    (:require [mid-fruits.css :as css]
              [re-frame.api :as r]
              [app.contents.frontend.api :as contents]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn about-us
  []
  (let [about-us @(r/subscribe [:db/get-item [:site :contents :about-us]])]
       [:section [:div#about-us [:div.kc-title "Magunkról"]
                                [contents/content-preview {:item-id (:content/id about-us)}]]]))

(defn view
  []
  [about-us])
