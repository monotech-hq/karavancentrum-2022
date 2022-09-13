
(ns app.common.frontend.context-menu.views
    (:require [x.app-elements.api :as elements]))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn context-menu-close-icon-button
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  [popup-id _]
  [elements/icon-button ::context-menu-close-icon-button
                        {:hover-color :highlight
                         :keypress    {:key-code 27}
                         :on-click    [:ui/close-popup! popup-id]
                         :preset      :close}])

(defn context-menu-label
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  [_ {:keys [label]}]
  [elements/label ::context-menu-label
                  {:color     :muted
                   :content   label
                   :font-size :xs
                   :indent    {:horizontal :xs :left :s}}])

(defn context-menu-label-bar
  ; @param (keyword) popup-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  [popup-id bar-props]
  [elements/horizontal-polarity ::context-menu-label-bar
                                {:start-content [context-menu-label             popup-id bar-props]
                                 :end-content   [context-menu-close-icon-button popup-id bar-props]}])
