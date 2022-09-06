
(ns app.common.frontend.surface.views
    (:require [layouts.surface-a.api :as surface-a]
              [x.app-elements.api    :as elements]))

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
                     [elements/label ::surface-label
                                     {:content     label
                                      :disabled?   disabled?
                                      :font-size   :xxl
                                      :font-weight :extra-bold
                                      :indent      {:left :xs}
                                      :placeholder placeholder}]]))

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
