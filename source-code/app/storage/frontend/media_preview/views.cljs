
(ns app.storage.frontend.media-preview.views
    (:require [app.storage.frontend.media-preview.prototypes :as media-preview.prototypes]
              [mid-fruits.random                             :as random]
              [mid-fruits.vector                             :as vector]
              [x.app-elements.api                            :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn media-preview-empty-thumbnail
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;   :show-empty? (boolean)(opt)}
  [editor-id {:keys [disabled? show-empty? thumbnail]}]
  (if show-empty? [elements/thumbnail {:border-radius :s
                                       :disabled?     disabled?
                                       :height        (:height thumbnail)
                                       :width         (:width  thumbnail)}]))

(defn media-preview-placeholder
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  [editor-id {:keys [disabled? placeholder]}]
  (if placeholder [elements/label {:color               :muted
                                   :content             placeholder
                                   :disabled?           disabled?
                                   :font-size           :xs
                                   :horizontal-position :left}]))

(defn media-preview-label
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)}
  [_ {:keys [disabled? info-text label]}]
  (if label [elements/label {:content   label
                             :disabled? disabled?
                             :info-text info-text}]))

(defn media-preview-thumbnail
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;   :thumbnail (map)
  ; @param (string) thumbnail-uri
  [preview-id {:keys [disabled? thumbnail] :as preview-props} thumbnail-uri]
  [elements/thumbnail {:border-radius :s
                       :disabled?     disabled?
                       :height        (:height thumbnail)
                       :width         (:width  thumbnail)
                       :uri           thumbnail-uri}])

(defn media-preview-thumbnails
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:thumbnail (map)
  ;    {:max-count (integer)(opt)}}
  [preview-id {:keys [media thumbnail] :as preview-props}]
  (let [media (vector/first-items media (:max-count thumbnail))]
       (letfn [(f [thumbnail-list thumbnail-uri] (conj thumbnail-list [media-preview-thumbnail preview-id preview-props thumbnail-uri]))]
              (if (vector/nonempty? media)
                  [:div {:style {:display "flex" :flex-wrap "wrap" :grid-gap "12px"}}
                        (reduce f [:<>] media)]
                  [:<> [media-preview-placeholder     preview-id preview-props]
                       [media-preview-empty-thumbnail preview-id preview-props]]))))

(defn media-preview-body
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  [preview-id preview-props]
  [:<> [media-preview-label      preview-id preview-props]
       [media-preview-thumbnails preview-id preview-props]])

(defn- media-preview
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:indent (map)(opt)}
  [preview-id {:keys [indent] :as preview-props}]
  [elements/blank preview-id
                  {:content [media-preview-body preview-id preview-props]
                   :indent  indent}])

(defn element
  ; @param (keyword)(opt) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;    Default: false
  ;   :indent (map)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :media (string or strings in vector)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :placeholder (metamorphic-content)(opt)
  ;   :show-empty? (boolean)(opt)
  ;    Default: false
  ;   :thumbnail (map)(opt)
  ;    {:max-count (integer)(opt)
  ;      Default: 8
  ;     :height (keyword)(opt)
  ;      :xxs, :xs, :s, :m, :l, :xl, :xxl, :3xl, :4xl, :5xl
  ;      Default: :5xl
  ;     :width (keyword)(opt)
  ;      :xxs, :xs, :s, :m, :l, :xl, :xxl, :3xl, :4xl, :5xl
  ;      Default: :5xl}
  ;
  ; @usage
  ;  [storage/media-preview {...}]
  ;
  ; @usage
  ;  [storage/media-preview :my-preview {...}]
  ([preview-props]
   [element (random/generate-keyword) preview-props])

  ([preview-id preview-props]
   (let [preview-props (media-preview.prototypes/preview-props-prototype preview-props)]
        [media-preview preview-id preview-props])))
