
(ns site.frontend.components.range-input
  (:require
    [reagent.core :as reagent :refer [atom]]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn percent [v]
  (str v "%"))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Events ----

(def min-gap 0)

(defn min-change [event min max]
  (let [value (js/parseInt (-> event .-target .-value))]
    (if (> value @max)
      (let [a @max]
        (reset! max value))
        ; (reset! min a))
      (reset! min value))))

(defn max-change [event min max]
  (let [value (js/parseInt (-> event .-target .-value))]
    (if (> @min value)
      (let [a @max]
        (reset! max @min)
        (reset! min value))
      (reset! max value))))

;this.bar.style.left = ((rangeValue1*(this.width-trackSize)/this.max) + (trackSize/2)) + 'px';

(defn bar-width [width min max]
   (- @max @min))

;; ---- Events ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn range-bar [min max]
  (let [width (bar-width 100 min max)]
    [:<>
       [:div.double-range__bar {:style {:width (percent width)}}]
       [:p]
       [:p {:style {:color "black" :padding-top "50px"}} (str width)]]))


(defn multi-range-slider [min max]
  (let [min-val (atom min)
        max-val (atom max)]
    (fn [min max]
      [:div
       [:span {:style {:color "black"}} (str @min-val)]
       [:span {:style {:color "black"}} (str " - "@max-val)]
       [:div.container
        [range-bar min-val max-val]
        [:div.slider-track]
        [:input {:id "min"
                 :on-input #(min-change % min-val max-val)
                 :type "range" :min min :max max}] ;:value @min-val}]
        [:input {:id "max"
                 :on-input #(max-change % min-val max-val)
                 :type "range" :min min :max max}]]]))) ;:value @max-val}]]])))


(defn view []
  [multi-range-slider 0 100])

;; ---- Components ----
;; -----------------------------------------------------------------------------
