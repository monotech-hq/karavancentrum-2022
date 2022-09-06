
(ns app.settings.frontend.view-selector.views
    (:require ;[settings.appearance-settings.views   :rename {body appearance-settings}]
              ;[settings.notification-settings.views :rename {body notification-settings}]
              ;[settings.personal-settings.views     :rename {body personal-settings}]
              ;[settings.privacy-settings.views      :rename {body privacy-settings}]
              ;[settings.view-selector.helpers       :as view-selector.helpers]
              ;[plugins.view-selector.api            :as view-selector]
              [app.common.frontend.api              :as common]
              [layouts.surface-a.api                :as surface-a]
              [x.app-core.api                       :as a]
              [x.app-elements.api                   :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn body-structure
  [_])
  ;(let [current-view-id @(a/subscribe [:view-selector/get-current-view-id :settings.view-selector])]
  ;     (case current-view-id :personal      [personal-settings     :settings.view-selector/view]
  ;                           :privacy       [privacy-settings      :settings.view-selector/view]
  ;                           :notifications [notification-settings :settings.view-selector/view]
  ;                           :appearance    [appearance-settings   :settings.view-selector/view]
  ;                                          [personal-settings     :settings.view-selector/view]])

(defn body
  [])
  ;[view-selector/body :settings.view-selector
  ;                    {:content         #'body-structure
  ;                     :default-view-id :personal])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn menu-bar
  [])
  ;[elements/menu-bar ::menu-bar
  ;                   {:menu-items (view-selector.helpers/menu-bar-items)
  ;                    :horizontal-align :center])

(defn header
  [_])
  ;[elements/horizontal-polarity ::header
  ;                              {:middle-content [menu-bar]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  [surface-id]
  [:<> [elements/horizontal-separator {:size :xxl}]
       [common/surface-label surface-id {:label :settings}]
       [elements/label {:content :there-is-no-available-settings
                        :color :highlight
                        :indent {:vertical :xs}}]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content [view-structure surface-id]}])
