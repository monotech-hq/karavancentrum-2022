
(ns app.contents.frontend.selector.views
    (:require [app.common.frontend.api               :as common]
              [app.components.frontend.api           :as components]
              [app.contents.frontend.handler.helpers :as handler.helpers]
              [elements.api                          :as elements]
              [engines.item-lister.api               :as item-lister]
              [hiccup.api                            :as hiccup]
              [layouts.popup-a.api                   :as popup-a]
              [re-frame.api                          :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn content-list-item
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ; @param (integer) item-dex
  ; @param (map) content-item
  [selector-id _ item-dex {:keys [body name id]}]
  (let [content-body (-> body handler.helpers/parse-content-body hiccup/to-string)]
       [components/item-list-row {:cells [[components/list-item-thumbnail {:icon :article :icon-family :material-icons-outlined}]
                                          [components/list-item-cell      {:rows [{:content name :placeholder :unnamed-content}
                                                                                  {:content content-body :font-size :xs :color :muted}]}]
                                          [components/list-item-gap {:width 6}]
                                          [common/selector-item-marker selector-id item-dex {:item-id id}]
                                          [components/list-item-gap {:width 6}]]
                                  :border (if (not= item-dex 0) :top)}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-lister
  []
  [item-lister/body :contents.selector
                    {:default-order-by :modified-at/descending
                     :items-path       [:contents :selector/downloaded-items]
                     :error-element    [components/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element    [components/ghost-view    {:layout :item-list :item-count 3}]
                     :list-element     [common/item-selector-body :contents.selector {:list-item-element #'content-list-item}]}])

(defn- body
  []
  [:<> [elements/horizontal-separator {:height :xs}]
       [content-lister]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [selector-disabled? @(r/subscribe [:item-lister/lister-disabled? :contents.selector])]
       [common/item-selector-control-bar :contents.selector
                                         {:disabled?        selector-disabled?
                                          :order-by-options [:modified-at/ascending :modified-at/descending :name/ascending :name/descending]
                                          :search-field-placeholder :search-in-contents
                                          :search-keys      [:name]}]))

(defn- label-bar
  []
  (let [multi-select? @(r/subscribe [:item-lister/get-meta-item :contents.selector :multi-select?])]
       [components/popup-label-bar :contents.selector/view
                                   {:primary-button   {:label :save! :on-click [:item-selector/save-selection! :contents.selector]}
                                    :secondary-button (if-let [autosaving? @(r/subscribe [:item-selector/autosaving? :contents.selector])]
                                                              {:label :abort!  :on-click [:item-selector/abort-autosave! :contents.selector]}
                                                              {:label :cancel! :on-click [:x.ui/remove-popup! :contents.selector/view]})
                                    :label            (if multi-select? :select-contents! :select-content!)}]))

(defn- header
  []
  [:<> [label-bar]
       (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :contents.selector])]
               [control-bar]
               [elements/horizontal-separator {:height :xxl}])])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (let [selected-content-count @(r/subscribe [:item-lister/get-selected-item-count :contents.selector])
        on-discard-selection [:item-lister/discard-selection! :contents.selector]]
       [common/item-selector-footer :contents.selector
                                    {:on-discard-selection on-discard-selection
                                     :selected-item-count  selected-content-count}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  ; @param (keyword) popup-id
  [popup-id]
  [popup-a/layout :contents.selector/view
                  {:body                #'body
                   :header              #'header
                   :footer              #'footer
                   :min-width           :m
                   :stretch-orientation :vertical}])
