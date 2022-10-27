
(ns site.karavancentrum.pages.main-page.frontend.sections.hero
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn scroll-down-icon
  []
  [:div#kc-scroll-icon [:span]])

(defn hero
  []
  [:div#kc-hero [scroll-down-icon]])

(defn view
  []
  [:section {:id :hero}
            [hero]])
