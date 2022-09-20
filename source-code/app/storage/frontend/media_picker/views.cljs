
(ns app.storage.frontend.media-picker.views
    (:require [app.storage.frontend.media-picker.prototypes :as media-picker.prototypes]
              [mid-fruits.candy                             :refer [param]]
              [mid-fruits.random                            :as random]
              [mid-fruits.vector                            :as vector]
              [x.app-core.api                               :as a]
              [x.app-elements.api                           :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn media-picker-toggle-auto-label
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:multiple? (boolean)(opt)}
  [picker-id {:keys [multiple?] :as picker-props}]
  (if-let [no-items-picked? @(a/subscribe [:storage.media-picker/no-items-picked? picker-id picker-props])]
          (if multiple? :no-items-selected :no-item-selected)
          (let [picked-item-count @(a/subscribe [:storage.media-picker/get-picked-item-count picker-id picker-props])]
               {:content :n-items-selected :replacements [picked-item-count]})))

(defn media-picker-toggle-label
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:toggle-label (metamorphic-content)(opt)}
  [picker-id {:keys [toggle-label] :as picker-props}]
  (let [toggle-label (or toggle-label (media-picker-toggle-auto-label picker-id picker-props))]
       [elements/label {:color     :muted
                        :content   toggle-label
                        :font-size :xs}]))

(defn media-picker-toggle
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)}
  [picker-id {:keys [disabled?] :as picker-props}]
  [elements/toggle {:content   [media-picker-toggle-label              picker-id picker-props]
                    :on-click  [:storage.media-selector/load-selector! picker-id picker-props]
                    :disabled? disabled?}])

(defn media-picker-label
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :required? (boolean)(opt)}
  [_ {:keys [disabled? info-text label required?]}]
  (if label [elements/label {:content   label
                             :disabled? disabled?
                             :info-text info-text
                             :required? required?}]))

(defn media-picker-thumbnail
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :thumbnails (map)
  ; @param (string) thumbnail-uri
  [picker-id {:keys [disabled? thumbnails] :as picker-props} thumbnail-uri]
  [elements/thumbnail {:border-radius :s
                       :disabled?     disabled?
                       :height        (:height thumbnails)
                       :width         (:width  thumbnails)
                       :uri           thumbnail-uri}])

(defn media-picker-thumbnail-list
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:thumbnails (map)
  ;    {:max-count (integer)(opt)}}
  [picker-id {:keys [thumbnails] :as picker-props}]
  (letfn [(f [thumbnail-list thumbnail-uri] (conj thumbnail-list [media-picker-thumbnail picker-id picker-props thumbnail-uri]))]
         (let [picked-items @(a/subscribe [:storage.media-picker/get-picked-items picker-id picker-props])]
              [:div {:style {:display :flex :flex-wrap :wrap :grid-column-gap "12px"}}
                    (reduce f [:<>] (if-let [max-count (:max-count thumbnails)]
                                            (vector/first-items picked-items max-count)
                                            (param              picked-items)))])))

(defn media-picker-thumbnails
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:thumbnails (map)(opt)}
  [picker-id {:keys [thumbnails] :as picker-props}]
  (if thumbnails [media-picker-thumbnail-list picker-id picker-props]))

(defn media-picker-body
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  [picker-id picker-props]
  [:<> [media-picker-label      picker-id picker-props]
       [media-picker-toggle     picker-id picker-props]
       [media-picker-thumbnails picker-id picker-props]])

(defn- media-picker
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:indent (map)(opt)}
  [picker-id {:keys [indent] :as picker-props}]
  [elements/blank picker-id
                  {:content [media-picker-body picker-id picker-props]
                   :indent  indent}])

(defn element
  ; @param (keyword)(opt) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;    Default: false
  ;   :extensions (strings in vector)(opt)
  ;   :indent (map)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :required? (boolean)(opt)
  ;   :multiple? (boolean)(opt)
  ;    Default: false
  ;   :thumbnails (map)(opt)
  ;    {:max-count (integer)(opt)
  ;      Default: 8
  ;     :height (keyword)(opt)
  ;      :xxs, :xs, :s, :m, :l, :xl, :xxl, :2xl, :3xl, :4xl
  ;      Default: :4xl
  ;     :width (keyword)(opt)
  ;      :xxs, :xs, :s, :m, :l, :xl, :xxl, :2xl, :3xl, :4xl
  ;      Default: :4xl}
  ;   :toggle-label (metamorphic-content)(opt)
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [storage/media-picker {...}]
  ;
  ; @usage
  ;  [storage/media-picker :my-picker {...}]
  ([picker-props]
   [element (random/generate-keyword) picker-props])

  ([picker-id picker-props]
   (let [picker-props (media-picker.prototypes/picker-props-prototype picker-props)]
        [media-picker picker-id picker-props])))
