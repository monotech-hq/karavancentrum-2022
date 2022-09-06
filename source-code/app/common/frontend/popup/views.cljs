
(ns app.common.frontend.popup.views
    (:require [x.app-core.api     :as a]
              [x.app-elements.api :as elements]))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn popup-label-bar-label
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/popup-label-bar-label :my-popup {...}]
  [_ {:keys [label]}]
  [elements/label ::popup-label-bar-label
                  {:content label
                   :indent  {:horizontal :xs}}])

(defn popup-secondary-button
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  ;  {:secondary-button-event (metamorphic-event)
  ;   :secondary-button-label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/popup-secondary-button :my-popup {...}]
  [_ {:keys [secondary-button-event secondary-button-label]}]
  [elements/button ::popup-secondary-button
                   {:font-size   :xs
                    :hover-color :highlight
                    :indent      {:horizontal :xxs :vertical :xxs}
                    :keypress    {:key-code 27}
                    :label       secondary-button-label
                    :on-click    secondary-button-event}])

(defn popup-primary-button
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  ;  {:primary-button-event (metamorphic-event)
  ;   :primary-button-label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/popup-primary-button :my-popup {...}]
  [_ {:keys [primary-button-event primary-button-label]}]
  ; Ha szeretnéd, hogy a popup primary-button gombjának eseménye megtörténjen
  ; az ENTER billentyű lenyomására akkor is, ha egy szövegmező fókuszált
  ; állapotban van, amely állapot letiltja a {:required? false} keypress
  ; eseményeket, akkor az egyes mezők {:on-enter ...} tulajdonságaként
  ; is add meg az eseményt!
  [elements/button ::popup-primary-button
                   {:color       :primary
                    :font-size   :xs
                    :hover-color :highlight
                    :indent      {:horizontal :xxs :vertical :xxs}
                    :keypress    {:key-code 13}
                    :label       primary-button-label
                    :on-click    primary-button-event}])

(defn popup-label-bar
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  ;  {:primary-button-event (metamorphic-event)
  ;   :primary-button-label (metamorphic-content)
  ;   :secondary-button-event (metamorphic-event)
  ;   :secondary-button-label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/popup-label-bar :my-popup {...}]
  [popup-id bar-props]
  [elements/horizontal-polarity ::popup-label-bar
                                {:start-content  [popup-secondary-button popup-id bar-props]
                                 :middle-content [popup-label-bar-label  popup-id bar-props]
                                 :end-content    [popup-primary-button   popup-id bar-props]}])

;; -- Saving-indicator components ---------------------------------------------
;; ----------------------------------------------------------------------------

(defn popup-saving-label
  ; @param (keyword) popup-id
  ; @param (map) indicator-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/popup-saving-label :my-popup {...}]
  [_ {:keys [label]}]
  [elements/label ::popup-saving-label
                  {:color   :muted
                   :content label}])

(defn popup-saving-indicator
  ; @param (keyword) popup-id
  ; @param (map) indicator-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/popup-saving-indicator :my-popup {...}]
  [popup-id indicator-props]
  [elements/column ::popup-saving-indicator
                   {:content             [popup-saving-label popup-id indicator-props]
                    :horizontal-align    :center
                    :stretch-orientation :vertical
                    :vertical-align      :center}])
