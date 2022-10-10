
(ns app.contents.frontend.preview.views
    (:require [app.common.frontend.api                  :as common]
              [app.contents.frontend.handler.helpers    :as handler.helpers]
              [app.contents.frontend.preview.prototypes :as preview.prototypes]
              [mid-fruits.random                        :as random]
              [plugins.item-preview.api                 :as item-preview]
              [re-frame.api                             :as r]
              [x.app-elements.api                       :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-preview-placeholder
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;   :item-id (string)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  [_ {:keys [disabled? item-id placeholder]}]
  (if (and placeholder (not item-id))
      [elements/label {:color       :muted
                       :content     placeholder
                       :disabled?   disabled?
                       :font-size   :xs
                       :selectable? true}]))

(defn- content-preview-content-body
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:color (keyword)
  ;   :disabled? (boolean)(opt)
  ;   :max-lines (integer)(opt)}
  [preview-id {:keys [color disabled? max-lines] :as preview-props}]
  (let [content-body @(r/subscribe [:db/get-item [:contents :preview/downloaded-items preview-id :body]])]
       [elements/text {:color     color
                       :content   (handler.helpers/parse-content-body content-body)
                       :disabled? disabled?
                       :font-size :s
                       :max-lines max-lines}]))

(defn- content-preview-element
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  [preview-id preview-props]
  [content-preview-content-body preview-id preview-props])

(defn- content-preview-label
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:disabled? (boolean)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)}
  [_ {:keys [disabled? info-text label]}]
  (if label [elements/label {:content   label
                             :disabled? disabled?
                             :info-text info-text}]))

(defn- content-preview-body
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {}
  [preview-id {:keys [item-id preview] :as preview-props}]
  [item-preview/body preview-id
                     {:ghost-element   #'common/item-preview-ghost-element
                      :error-element   [common/error-element {:error :the-content-has-been-broken}]
                      :preview-element [content-preview-element preview-id preview-props]
                      :item-id         item-id
                      :item-path       [:contents :preview/downloaded-items preview-id]
                      :transfer-id     :contents.preview}])

(defn- content-preview
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:indent (map)(opt)}
  [preview-id {:keys [indent] :as preview-props}]
  [elements/blank preview-id
                  {:content [:<> [content-preview-label       preview-id preview-props]
                                 [content-preview-body        preview-id preview-props]
                                 [content-preview-placeholder preview-id preview-props]]
                   :indent  indent}])

(defn element
  ; @param (keyword)(opt) preview-id
  ; @param (map) preview-props
  ;  {:color (keyword)(opt)
  ;    :default, :highlight, :muted
  ;    Default: :muted
  ;   :disabled? (boolean)(opt)
  ;    Default: false
  ;   :indent (map)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :item-id (string)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :max-lines (integer)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [contents/content-preview {...}]
  ;
  ; @usage
  ;  [contents/content-preview :my-preview {...}]
  ([preview-props]
   [element (random/generate-keyword) preview-props])

  ([preview-id preview-props]
   (let [preview-props (preview.prototypes/preview-props-prototype preview-id preview-props)]
        [content-preview preview-id preview-props])))
