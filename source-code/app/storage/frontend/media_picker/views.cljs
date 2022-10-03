
(ns app.storage.frontend.media-picker.views
    (:require [app.storage.frontend.media-picker.prototypes :as media-picker.prototypes]
              [app.storage.frontend.media-preview.views     :as media-preview.views]
              [mid-fruits.random                            :as random]
              [re-frame.api                                 :as r]
              [x.app-elements.api                           :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn media-picker-toggle-auto-label
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:multi-select? (boolean)(opt)}
  [picker-id {:keys [multi-select?] :as picker-props}]
  (if-let [no-items-picked? @(r/subscribe [:storage.media-picker/no-items-picked? picker-id picker-props])]
          (if multi-select? :no-items-selected :no-item-selected)
          (let [picked-item-count @(r/subscribe [:storage.media-picker/get-picked-item-count picker-id picker-props])]
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

(defn media-picker-button
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)}
  [picker-id {:keys [disabled?] :as picker-props}]
  [:div {:style {:display :flex}}
        [elements/toggle {:content   [media-picker-toggle-label              picker-id picker-props]
                          :on-click  [:storage.media-selector/load-selector! picker-id picker-props]
                          :disabled? disabled?}]])

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

(defn media-picker-preview
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  [picker-id picker-props]
  (let [preview-props (media-picker.prototypes/preview-props-prototype picker-id picker-props)]
       [media-preview.views/element picker-id preview-props]))

(defn media-picker-body
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  [picker-id picker-props]
  [:<> [media-picker-label   picker-id picker-props]
       [media-picker-button  picker-id picker-props]
       [media-picker-preview picker-id picker-props]])

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
  ;  {:autosave? (boolean)(opt)
  ;   Default: false
  ;   :disabled? (boolean)(opt)
  ;    Default: false
  ;   :extensions (strings in vector)(opt)
  ;   :indent (map)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :multi-select? (boolean)(opt)
  ;    Default: false
  ;   :no-media-label (metamorphic-content)(opt)
  ;   :on-save (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elemet.
  ;   :required? (boolean)(opt)
  ;   :thumbnail (map)(opt)
  ;    {:max-count (integer)(opt)
  ;      Default: 8
  ;     :height (keyword)(opt)
  ;      :xxs, :xs, :s, :m, :l, :xl, :xxl, :3xl, :4xl, :5xl
  ;      Default: :5xl
  ;     :width (keyword)(opt)
  ;      :xxs, :xs, :s, :m, :l, :xl, :xxl, :3xl, :4xl, :5xl
  ;      Default: :5xl}
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
   (let [picker-props (media-picker.prototypes/picker-props-prototype picker-id picker-props)]
        [media-picker picker-id picker-props])))
