
(ns site.karavancentrum.pages.main-page.frontend.sections.hero
    (:require [re-frame.api                 :as r]
              [site.components.frontend.api :as components]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn hero
  []
  [:div#kc-hero [components/scroll-icon {:style {:position :absolute :bottom 0 :left 0}}]])

(defn view
  []
  [:section {:id :hero}
            [hero]])
