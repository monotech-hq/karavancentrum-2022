
(ns site.karavancentrum.pages.main-page.frontend.sections.hero
    (:require [re-frame.api             :as r]
              [site.common.frontend.api :as common]))
 
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
  [:section [common/fragment-sensor :fooldal]
            [hero]])
