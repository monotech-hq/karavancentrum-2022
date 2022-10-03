
(ns app.contents.frontend.selector.views
    (:require [app.common.frontend.api               :as common]
              [app.contents.frontend.handler.helpers :as handler.helpers]
              [layouts.popup-a.api                   :as popup-a]
              [mid-fruits.hiccup                     :as hiccup]
              [plugins.item-lister.api               :as item-lister]
              [x.app-components.api                  :as components]
              [x.app-core.api                        :as a]
              [x.app-elements.api                    :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn content-item-structure
  [lister-id item-dex {:keys [body name id modified-at] :as content-item}]
  (let [timestamp   @(a/subscribe [:activities/get-actual-timestamp modified-at])
        content-body (-> body handler.helpers/parse-content-body hiccup/to-string)]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail-icon lister-id item-dex {:icon :article :icon-family :material-icons-outlined}]
                                            [common/list-item-primary-cell   lister-id item-dex {:label name :timestamp timestamp :stretch? true}]
                                            (if-let [content-selected? @(a/subscribe [:item-lister/item-selected? :contents.selector id])]
                                                    (if-let [autosaving? @(a/subscribe [:item-selector/autosaving? :contents.selector])]
                                                            [common/list-item-end-icon lister-id item-dex {:icon :check_circle_outline :progress 100 :progress-duration 1000}]
                                                            [common/list-item-end-icon lister-id item-dex {:icon :check_circle_outline}])
                                                    [common/list-item-end-icon lister-id item-dex {:icon :radio_button_unchecked}])]}]))

(defn content-item
  [lister-id item-dex {:keys [id] :as content-item}]
  [elements/toggle {:content     [content-item-structure lister-id item-dex content-item]
                    :hover-color :highlight
                    :on-click    [:item-selector/item-clicked :contents.selector id]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-lister
  []
  [item-lister/body :contents.selector
                    {:default-order-by :modified-at/descending
                     :items-path       [:contents :selector/downloaded-items]
                     :error-element    [common/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element    #'common/item-selector-ghost-element
                     :list-element     #'content-item}])

(defn- body
  []
  [:<> [elements/horizontal-separator {:size :xs}]
       [content-lister]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [selector-disabled? @(a/subscribe [:item-lister/lister-disabled? :contents.selector])]
       [common/item-selector-control-bar :contents.selector
                                         {:disabled?        selector-disabled?
                                          :order-by-options [:modified-at/ascending :modified-at/descending :name/ascending :name/descending]
                                          :search-field-placeholder :search-in-contents
                                          :search-keys      [:name]}]))

(defn- label-bar
  []
  (let [multi-select? @(a/subscribe [:item-lister/get-meta-item :contents.selector :multi-select?])]
       [common/popup-label-bar :contents.selector/view
                               {:primary-button   {:label :save! :on-click [:item-selector/save-selection! :contents.selector]}
                                :secondary-button (if-let [autosaving? @(a/subscribe [:item-selector/autosaving? :contents.selector])]
                                                          {:label :abort!  :on-click [:item-selector/abort-autosave! :contents.selector]}
                                                          {:label :cancel! :on-click [:ui/close-popup! :contents.selector/view]})
                                :label            (if multi-select? :select-contents! :select-content!)}]))

(defn- header
  []
  [:<> [label-bar]
       (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :contents.selector])]
               [control-bar]
               [elements/horizontal-separator {:size :xxl}])])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (let [selected-content-count @(a/subscribe [:item-lister/get-selected-item-count :contents.selector])
        on-discard-selection [:item-lister/discard-selection! :contents.selector]]
       [common/item-selector-footer :contents.selector
                                    {:on-discard-selection on-discard-selection
                                     :selected-item-count  selected-content-count}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  [selector-id]
  [popup-a/layout :contents.selector/view
                  {:body                #'body
                   :header              #'header
                   :footer              #'footer
                   :min-width           :m
                   :stretch-orientation :vertical}])
