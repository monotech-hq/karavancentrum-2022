
(ns site.karavancentrum-hu.pages.vehicle-page.frontend.slider
    (:require [reagent.core                :as reagent]
              ["react-responsive-carousel" :refer [Carousel]]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def DEFAULT-CONFIG {:emulateTouch   true
                     :infiniteLoop   true
                     :showThumbs     false
                     :showIndicators false
                     :showStatus     false})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn slider
  [data]
  (let [configurations DEFAULT-CONFIG]
       [:div [:> Carousel configurations
                 data]]))

(defn view
  [& data]
  [:div [slider data]])
