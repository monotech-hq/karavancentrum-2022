
(ns app.common.frontend.browser.views
    (:require [app.common.frontend.surface.views :as surface.views]
              [app.common.frontend.lister.views  :as lister.views]
              [mid-fruits.candy                  :refer [param]]
              [mid-fruits.keyword                :as keyword]
              [x.app-components.api              :as components]
              [x.app-core.api                    :as a]
              [x.app-elements.api                :as elements]))

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
  (let [data-received?    @(a/subscribe [:item-browser/data-received?    browser-id])
        browser-disabled? @(a/subscribe [:item-browser/browser-disabled? browser-id])]
       [surface.views/surface-description browser-id
                                          {:description description
                                           :disabled?   browser-disabled?
                                           :loading?    (not data-received?)}]))

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
       [surface.views/surface-label browser-id
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
          [:<> [item-browser-label       browser-id bar-props]
               [item-browser-description browser-id bar-props]]))
