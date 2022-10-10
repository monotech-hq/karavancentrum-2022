
(ns site.karavancentrum.pages.vehicle.frontend.slider
  (:require [reagent.core                :as reagent]
            [x.app-core.api              :as a :refer [r]]
            [x.app-environment.api       :as env]
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
