
(ns app.common.frontend.item-browser.views
    (:require [app.common.frontend.item-lister.views :as lister.views]
              [app.common.frontend.surface.views     :as surface.views]
              [mid-fruits.candy                      :refer [param]]
              [mid-fruits.keyword                    :as keyword]
              [x.app-components.api                  :as components]
              [x.app-core.api                        :as a]
              [x.app-elements.api                    :as elements]))

;; -- Search-block components -------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-browser-search-field
  ; @param (keyword) browser-id
  ; @param (map) block-props
  ;  {:field-placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-browser-search-field :my-browser {...}]
  [browser-id {:keys [field-placeholder]}]
  (if-let [data-received? @(a/subscribe [:item-browser/data-received? browser-id])]
          (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? browser-id])
                search-event [:item-browser/search-items! browser-id {:search-keys [:name]}]]
               [elements/search-field ::search-items-field
                                      {:autoclear?    true
                                       :autofocus?    true
                                       :disabled?     browser-disabled?
                                       :indent        {:left :xs :right :xxs}
                                       :on-empty      search-event
                                       :on-type-ended search-event
                                       :placeholder   field-placeholder}])
          [elements/ghost {:height :l :indent {:left :xs :right :xxs}}]))

(defn item-browser-search-description
  ; @param (keyword) browser-id
  ; @param (map) block-props
  ;
  ; @usage
  ;  [common/item-browser-search-description :my-browser {...}]
  [browser-id _]
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled?  browser-id])
        all-item-count    @(a/subscribe [:item-browser/get-all-item-count browser-id])
        description        (components/content {:content :search-results-n :replacements [all-item-count]})]
       [elements/label ::search-items-description
                       {:color     :muted
                        :content   (if-not browser-disabled? description)
                        :font-size :xxs
                        :indent    {:top :s :left :xs}}]))

(defn item-browser-search-block
  ; @param (keyword) browser-id
  ; @param (map) block-props
  ;  {:field-placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-browser-search-block :my-browser {...}]
  [browser-id block-props]
  (if-let [error-mode? @(a/subscribe [:item-browser/get-meta-item browser-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-browser felületen nem jelenik meg!
          [:<> [item-browser-search-description browser-id block-props]
               [item-browser-search-field       browser-id block-props]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-browser-breadcrumbs
  ; @param (keyword) browser-id
  ; @param (map) breadcrumbs-props
  ;  {:crumbs (maps in vector)}
  ;
  ; @usage
  ;  [common/item-browser-breadcrumbs :my-browser {...}]
  [browser-id {:keys [crumbs]}]
  (if-let [error-mode? @(a/subscribe [:item-browser/get-meta-item browser-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-browser felületen nem jelenik meg!
          (let [data-received?    @(a/subscribe [:item-browser/data-received?    browser-id])
                browser-disabled? @(a/subscribe [:item-browser/browser-disabled? browser-id])]
               [surface.views/surface-breadcrumbs nil
                                                  {:crumbs    crumbs
                                                   :disabled? browser-disabled?
                                                   :loading?  (not data-received?)}])))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-browser-description
  ; @param (keyword) browser-id
  ; @param (map) bar-props
  ;  {:description (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-browser-description :my-browser {...}]
  [browser-id {:keys [description]}]
  (if-let [data-received? @(a/subscribe [:item-browser/data-received? browser-id])]
          (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? browser-id])]
               [elements/label ::item-browser-description
                               {:color     :muted
                                :content   description
                                :disabled? browser-disabled?
                                :font-size :xxs
                                :indent    {:left :s :top :s}}])))


(defn item-browser-label
  ; @param (keyword) browser-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-browser-label :my-browser {...}]
  [browser-id {:keys [label]}]
  (let [data-received?    @(a/subscribe [:item-browser/data-received?    browser-id])
        browser-disabled? @(a/subscribe [:item-browser/browser-disabled? browser-id])]
       [surface.views/surface-label nil
                                    {:disabled? browser-disabled?
                                     :label     label
                                     :loading?  (not data-received?)}]))

(defn item-browser-label-bar
  ; @param (keyword) browser-id
  ; @param (map) bar-props
  ;  {:description (metamorphic-content)(opt)
  ;   :label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-browser-label-bar :my-browser {...}]
  [browser-id {:keys [create-item-uri] :as bar-props}]
  (if-let [error-mode? @(a/subscribe [:item-browser/get-meta-item browser-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-browser felületen nem jelenik meg!
          [:div {:style {:display :flex}}
                [item-browser-label       browser-id bar-props]
                [item-browser-description browser-id bar-props]]))
