
(ns app.views.frontend.error-screen.views
    (:require [layouts.surface-a.api :as surface-a]
              [x.app-elements.api    :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- error-title
  [surface-id {:keys [title]}]
  [elements/text ::error-title
                 {:content          title
                  :font-size        :xxl
                  :font-weight      :bold
                  :horizontal-align :center
                  :indent           {:top :xxl :vertical :xs}
                  :selectable?      false}])

(defn- error-helper
  [surface-id {:keys [helper]}]
  [elements/text ::error-helper
                 {:content          helper
                  :font-size        :s
                  :horizontal-align :center
                  :indent           {:vertical :xs}
                  :selectable?      false}])

(defn- error-icon
  [surface-id {:keys [icon]}]
  (if icon [elements/row ::error-icon-wrapper
                         {:content [elements/icon ::error-icon
                                                  {:icon icon
                                                   :size :xxl}]
                          :horizontal-align :center}]))

(defn- go-back-button
  [surface-id content-props]
  [elements/button ::go-back-button
                   {:border-radius :s
                    :hover-color   :highlight
                    :indent        {:top :m}
                    :label         :back!
                    :on-click      [:router/go-back!]}])

(defn- go-back
  [surface-id content-props]
  [elements/row ::go-back
                {:content [go-back-button surface-id content-props]
                 :horizontal-align :center}])

(defn- view-structure
  [surface-id content-props]
  [:<> [elements/horizontal-separator {:size :xxl}]
       [error-icon   surface-id content-props]
       [error-title  surface-id content-props]
       [error-helper surface-id content-props]
       [go-back      surface-id content-props]])

(defn view
  [surface-id content-props]
  [surface-a/layout surface-id
                    {:content [view-structure surface-id content-props]}])
