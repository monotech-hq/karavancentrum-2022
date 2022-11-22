
(ns app.common.frontend.item-browser.views
    (:require [app.common.frontend.item-browser.prototypes :as item-browser.prototypes]
              [app.common.frontend.item-lister.views       :as item-lister.views]
              [app.components.frontend.api                 :as components]
              [elements.api                                :as elements]
              [engines.item-browser.api                    :as item-browser]
              [random.api                                  :as random]
              [re-frame.api                                :as r]))

;; -- Search components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-browser-search-field
  ; @param (keyword) browser-id
  ; @param (map) field-props
  ;  {:disabled? (boolean)(opt)
  ;   :placeholder (metamorphic-content)(opt)
  ;   :search-keys (keywords in vector)}
  ;
  ; @usage
  ;  [item-browser-search-field :my-browser {...}]
  [browser-id field-props]
  [item-lister.views/item-lister-search-field browser-id field-props])

(defn item-browser-search-description
  ; @param (keyword) browser-id
  ; @param (map) description-props
  ;  {:disabled? (boolean)(opt)}
  ;
  ; @usage
  ;  [item-browser-search-description :my-browser {...}]
  [browser-id description-props]
  [item-lister.views/item-lister-search-description browser-id description-props])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- item-list-body
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  ;  {:list-item-element (component or symbol)}
  [browser-id {:keys [list-item-element] :as browser-props}]
  (let [downloaded-items @(r/subscribe [:item-browser/get-downloaded-items browser-id])]
       (letfn [(f [item-list item-dex item]
                  (conj item-list [list-item-element browser-id browser-props item-dex item]))]
              (reduce-kv f [:<>] downloaded-items))))

(defn- item-list-header
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  ;  {:item-list-header (component or symbol)(opt)}
  [browser-id {:keys [item-list-header] :as browser-props}]
  (if item-list-header [item-list-header browser-id browser-props]))

(defn- item-list
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  [browser-id browser-props]
  [components/item-list-table browser-id
                              {:body   [item-list-body   browser-id browser-props]
                               :header [item-list-header browser-id browser-props]}])

(defn- item-browser
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  [browser-id browser-props]
  (let [browser-props (assoc browser-props :error-element [components/error-content {:error :the-content-you-opened-may-be-broken}]
                                           :ghost-element [components/ghost-view    {:layout :item-list :item-count 3}]
                                           :list-element  [item-list browser-id browser-props])]
       [item-browser/body browser-id browser-props]))

(defn element
  ; @param (keyword)(opt) browser-id
  ; @param (map) browser-props
  ;  {:item-list-header (component or symbol)(opt)
  ;   :list-item-element (component or symbol)(opt)}
  ;
  ; @usage
  ;  [item-browser {...}]
  ;
  ; @usage
  ;  [item-browser :my-item-browser {...}]
  ;
  ; @usage
  ;  (defn my-item-element [browser-id browser-props item-dex item] ...)
  ;  [item-browser :my-item-browser {:item-element  #'my-item-element}]
  ;
  ; @usage
  ;  (defn my-item-list-header  [browser-id browser-props] ...)
  ;  (defn my-list-item-element [browser-id browser-props item-dex item drag-props] ...)
  ;  [item-browser :my-item-browser {:item-list-header  #'my-item-list-header
  ;                                  :list-item-element #'my-list-item-element
  ;                                  :sortable?         true}]
  ([browser-props]
   [element (random/generate-keyword) browser-props])

  ([browser-id browser-props]
   (let [];browser-props (item-browser.prototypes/browser-props-prototype browser-id browser-props)
        [item-browser browser-id browser-props])))
