
(ns app.contents.frontend.selector.views
    (:require [app.common.frontend.api :as common]
              [layouts.popup-a.api     :as popup-a]
              [plugins.item-lister.api :as item-lister]
              [x.app-components.api    :as components]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn content-item-structure
  [lister-id item-dex {:keys [name id modified-at] :as content-item}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail-icon lister-id item-dex {:icon :article :icon-family :material-icons-outlined}]
                                            [common/list-item-primary-cell   lister-id item-dex {:label name :timestamp timestamp :stretch? true}]
                                            (if-let [content-selected? @(a/subscribe [:item-lister/item-selected? :contents.content-selector id])]
                                                    (if-let [autosaving? @(a/subscribe [:item-selector/autosaving? :contents.content-selector])]
                                                            [common/list-item-end-icon lister-id item-dex {:icon :check_circle_outline :progress 100 :progress-duration 1000}]
                                                            [common/list-item-end-icon lister-id item-dex {:icon :check_circle_outline}])
                                                    [common/list-item-end-icon lister-id item-dex {:icon :radio_button_unchecked}])]}]))

(defn content-item
  [lister-id item-dex {:keys [id] :as content-item}]
  [elements/toggle {:content     [content-item-structure lister-id item-dex content-item]
                    :hover-color :highlight
                    :on-click    [:item-selector/item-clicked :contents.content-selector id]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-lister
  []
  [item-lister/body :contents.content-selector
                    {:default-order-by :modified-at/descending
                     :items-path       [:contents :content-selector/downloaded-items]
                     :ghost-element    #'common/item-selector-body-ghost-view
                     :list-element     #'content-item}])

(defn- body
  []
  [:<> [elements/horizontal-separator {:size :xs}]
       [content-lister]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  [common/item-selector-control-bar :contents.content-selector
                                    {:order-by-options [:modified-at/ascending :modified-at/descending :name/ascending :name/descending]
                                     :search-field-placeholder :search-in-contents
                                     :search-keys [:name]}])

(defn- label-bar
  []
  [common/popup-label-bar :contents.content-selector/view
                          {:primary-button   {:label :save! :on-click [:item-selector/save-selection! :contents.content-selector]}
                           :secondary-button (if-let [autosaving? @(a/subscribe [:item-selector/autosaving? :contents.content-selector])]
                                                     {:label :abort!  :on-click [:item-selector/abort-autosave! :contents.content-selector]}
                                                     {:label :cancel! :on-click [:ui/close-popup! :contents.content-selector/view]})
                           :label :select-content!}])

(defn- header
  []
  [:<> [label-bar]
       (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :contents.content-selector])]
               [control-bar]
               [elements/horizontal-separator {:size :xxl}])])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (let [selected-content-count @(a/subscribe [:item-lister/get-selected-item-count :contents.content-selector])]
       [common/item-selector-footer :contents.content-selector
                                    {:on-discard [:item-lister/discard-selection! :contents.content-selector]
                                     :selected-item-count selected-content-count}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  [selector-id]
  [popup-a/layout :contents.content-selector/view
                  {:body                #'body
                   :header              #'header
                   :footer              #'footer
                   :min-width           :m
                   :stretch-orientation :vertical}])
