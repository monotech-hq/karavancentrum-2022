
(ns app.common.frontend.item-browser.views
    (:require [app.common.frontend.item-lister.views :as item-lister.views]
              [x.app-components.api                  :as components]
              [x.app-core.api                        :as a]
              [x.app-elements.api                    :as elements]))

;; -- Search-block components -------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-browser-search-field
  ; @param (keyword) browser-id
  ; @param (map) block-props
  ;  {:disabled? (boolean)(opt)
  ;   :field-placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-browser-search-field :my-browser {...}]
  [browser-id {:keys [disabled? field-placeholder]}]
  (let [viewport-small? @(a/subscribe [:environment/viewport-small?])
        search-event [:item-browser/search-items! browser-id {:search-keys [:name]}]]
       [elements/search-field ::search-items-field
                              {:autoclear?    true
                               :autofocus?    true
                               :border-radius (if viewport-small? :none :l)
                               :disabled?     disabled?
                               :on-empty      search-event
                               :on-type-ended search-event
                               :placeholder   field-placeholder}]))

(defn item-browser-search-description
  ; @param (keyword) browser-id
  ; @param (map) block-props
  ;  {:disabled? (boolean)(opt)}
  ;
  ; @usage
  ;  [common/item-browser-search-description :my-browser {...}]
  [browser-id {:keys [disabled?]}]
  (let [all-item-count @(a/subscribe [:item-browser/get-all-item-count browser-id])
        description      (components/content {:content :search-results-n :replacements [all-item-count]})]
       [elements/label ::search-items-description
                       {:color     :muted
                        :content   (if-not disabled? description)
                        :font-size :xxs
                        :indent    {:top :m :left :xs}}]))

(defn item-browser-search-block
  ; @param (keyword) browser-id
  ; @param (map) block-props
  ;  {:disabled? (boolean)(opt)
  ;   :field-placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-browser-search-block :my-browser {...}]
  [browser-id block-props]
  [:<> [item-browser-search-description browser-id block-props]
       [item-browser-search-field       browser-id block-props]])
