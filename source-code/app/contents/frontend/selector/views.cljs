
(ns app.contents.frontend.selector.views
    (:require [app.common.frontend.api :as common]
              [layouts.popup-a.api     :as popup-a]
              [plugins.item-lister.api :as item-lister]
              [x.app-components.api    :as components]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; -- Header components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  [common/item-selector-control-bar :contents.content-selector
                                    {:order-by-options [:modified-at/ascending :modified-at/descending :name/ascending :name/descending]
                                     :search-field-placeholder :search-in-contents
                                     :search-keys [:name]}])

(defn header
  [selector-id]
  (let [saving? @(a/subscribe [:db/get-item [:contents :content-selector/meta-items :saving?]])]
       (if-not saving? [:<> [common/popup-label-bar :contents.content-selector/view
                                                    {:primary-button   {:on-click [:contents.content-selector/save-selected-item!]
                                                                        :label    :save!}
                                                     :secondary-button {:on-click [:ui/close-popup! :contents.content-selector/view]
                                                                        :label    :cancel!}
                                                     :label :select-content!}]
                            (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :contents.content-selector])]
                                    [control-bar]
                                    [elements/horizontal-separator {:size :xxl}])])))

;; -- Footer components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn footer
  [selector-id]
  (let [saving?             @(a/subscribe [:db/get-item [:contents :content-selector/meta-items :saving?]])
        selected-item-count @(a/subscribe [:contents.content-selector/get-selected-item-count])]
       (if-not saving? [common/item-selector-footer :contents.content-selector
                                                    {:on-discard [:contents.content-selector/discard-selection!]
                                                     :selected-item-count selected-item-count}])))

;; -- Body components ---------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn content-item-structure
  [lister-id item-dex {:keys [name id modified-at] :as content-item}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail-icon lister-id item-dex {:icon :article :icon-family :material-icons-outlined}]
                                            [common/list-item-primary-cell   lister-id item-dex {:label name :timestamp timestamp :stretch? true}]
                                            (if-let [item-selected? @(a/subscribe [:contents.content-selector/item-selected? content-item])]
                                                    [common/list-item-end-icon lister-id item-dex {:icon :check_circle_outline}]
                                                    [common/list-item-end-icon lister-id item-dex {:icon :radio_button_unchecked}])]}]))

(defn content-item
  [lister-id item-dex content-item]
  [elements/toggle {:content     [content-item-structure lister-id item-dex content-item]
                    :hover-color :highlight
                    :on-click    [:contents.content-selector/item-clicked content-item]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-lister-ghost-view :contents.content-selector
                                 {:padding "0 24px"}])

(defn- saving-indicator
  []
  (let [saving-label (components/content {:content :n-items-selected :replacements ["1"]})]
       [common/popup-progress-indicator :contents.content-selector/view
                                        {:label saving-label}]))

(defn- content-lister
  []
  [item-lister/body :contents.content-selector
                    {:default-order-by :modified-at/descending
                     :ghost-element    #'ghost-view
                     :items-path       [:contents :content-selector/downloaded-items]
                     :list-element     #'content-item}])

(defn body
  [selector-id]
  (if-let [saving? @(a/subscribe [:db/get-item [:contents :content-selector/meta-items :saving?]])]
          [saving-indicator]
          [:<> [elements/horizontal-separator {:size :xs}]
               [content-lister]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  [selector-id]
  [popup-a/layout :contents.content-selector/view
                  {:body                [body   selector-id]
                   :header              [header selector-id]
                   :footer              [footer selector-id]
                   :min-width           :m
                   :stretch-orientation :vertical}])
