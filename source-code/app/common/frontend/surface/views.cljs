
(ns app.common.frontend.surface.views
    (:require [layouts.surface-a.api :as surface-a]
              [x.app-core.api        :as a]
              [x.app-elements.api    :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- go-back-button
  [_]
  [:div {:style {:display :flex :justify-content :center}}
        [elements/button ::go-back-button
                         {:border-radius :s
                          :hover-color   :highlight
                          :indent        {:top :m}
                          :label         :back!
                          :on-click      [:router/go-back!]}]])

;; -- Breadcrumbs components --------------------------------------------------
;; ----------------------------------------------------------------------------

(defn surface-breadcrumbs
  ; @param (keyword) surface-id
  ; @param (map) breadcrumbs-props
  ;  {:crumbs (maps in vector)
  ;   :disabled? (boolean)(opt)
  ;   :loading? (boolean)(opt)}
  ;
  ; @usage
  ;  [common/surface-breadcrumbs :my-surface {...}]
  [surface-id {:keys [crumbs disabled? loading?]}]
  (if loading? [:div {:style {:display :flex}}
                     (letfn [(f [ghost-list _]
                                (conj ghost-list [elements/ghost {:height :xs :indent {:left :xs :top :xxs} :style {:width "80px"}}]))]
                            (reduce f [:<>] crumbs))]
               [elements/breadcrumbs ::breadcrumbs
                                     {:crumbs    crumbs
                                      :disabled? disabled?
                                      :indent    {:left :xs}}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn surface-label
  ; @param (keyword) surface-id
  ; @param (map) description-props
  ;  {:disabled? (boolean)(opt)
  ;   :label (metamorphic-content)
  ;   :loading? (boolean)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/surface-label :my-surface {...}]
  [surface-id {:keys [disabled? label loading? placeholder]}]
  (if loading? [elements/ghost {:height :l :indent {:bottom :xs :left :xs} :style {:width "240px"}}]
               ; Ha nem egy közös elemben (pl. div) volt a sensor és a label, akkor bizonoyos
               ; esetekben (pl. horizontal-polarity elemben) nem megfelelő helyen érzékelt a sensor
               [:div [surface-a/title-sensor {:title label :offset -12}]
                     (let [viewport-small? @(a/subscribe [:environment/viewport-small?])]
                          [elements/label ::surface-label
                                          {:content     label
                                           :disabled?   disabled?
                                           :font-size   (if viewport-small? :l :xxl)
                                           :font-weight :extra-bold
                                           :indent      {:left :xs}
                                           :placeholder placeholder}])]))

(defn surface-description
  ; @param (keyword) surface-id
  ; @param (map) description-props
  ;  {:description (metamorphic-content)
  ;   :disabled? (boolean)(opt)
  ;   :loading? (boolean)(opt)}
  ;
  ; @usage
  ;  [common/surface-description :my-surface {...}]
  [surface-id {:keys [description disabled? loading?]}]
  (if loading? [elements/ghost {:height :s :indent {:left :xs} :style {:width "180px"}}]
               [elements/label ::surface-description
                               {:color     :muted
                                :content   description
                                :disabled? disabled?
                                :font-size :xxs
                                :indent    {:left :xs}}]))
