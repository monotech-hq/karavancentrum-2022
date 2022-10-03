
(ns site.karavancentrum.pages.vehicle.frontend.slider
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-environment.api :as env]

   [reagent.core :as reagent]

   ["react-responsive-carousel" :refer [Carousel]]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def DEFAULT-CONFIG
  {:emulateTouch   true
   :infiniteLoop   true
   :showThumbs     false
   :showIndicators false
   :showStatus     false})


;; ---- Configurations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn slider [data]
  (let [configurations DEFAULT-CONFIG]
    [:div
     [:> Carousel configurations
      data]]))

(defn view [& data]
  [:div
   [slider data]])

;; ---- Components ----
;; -----------------------------------------------------------------------------
