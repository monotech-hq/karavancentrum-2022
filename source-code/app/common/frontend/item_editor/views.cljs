
(ns app.common.frontend.item-editor.views
    (:require [app.common.frontend.surface.views :as surface.views]
              [mid-fruits.vector                 :as vector]
              [x.app-core.api                    :as a]
              [x.app-elements.api                :as elements]))

;; -- Breadcrumbs components --------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-breadcrumbs
  ; @param (keyword) editor-id
  ; @param (map) breadcrumbs-props
  ;  {:crumbs (maps in vector)}
  ;
  ; @usage
  ;  [common/item-editor-breadcrumbs :my-editor {...}]
  [editor-id {:keys [crumbs]}]
  (if-let [error-mode? @(a/subscribe [:item-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          (let [data-received?   @(a/subscribe [:item-editor/data-received?   editor-id])
                editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
               [surface.views/surface-breadcrumbs nil
                                                  {:crumbs    crumbs
                                                   :disabled? editor-disabled?
                                                   :loading?  (not data-received?)}])))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn revert-item-icon-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/revert-item-icon-button :my-editor {...}]
  [editor-id _]
  (if-let [data-received? @(a/subscribe [:item-editor/data-received? editor-id])]
          (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])
                item-changed?    @(a/subscribe [:item-editor/item-changed?    editor-id])]
               [elements/icon-button ::revert-item-icon-button
                                     {:color       :default
                                      :disabled?   (or editor-disabled? (not item-changed?))
                                      :hover-color :highlight
                                      :icon        :settings_backup_restore
                                      :on-click    [:item-editor/revert-item! editor-id]}])))

(defn save-item-icon-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/save-item-icon-button :my-editor {...}]
  [editor-id _]
  (if-let [data-received? @(a/subscribe [:item-editor/data-received? editor-id])]
          (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
               [elements/icon-button ::save-item-icon-button
                                     {:color       :primary
                                      :disabled?   editor-disabled?
                                      :hover-color :highlight
                                      :icon        :save
                                      :on-click    [:item-editor/save-item! editor-id]}])))

(defn item-name-label
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-name-label :my-editor {...}]
  [editor-id {:keys [label placeholder]}]
  (let [data-received?   @(a/subscribe [:item-editor/data-received?   editor-id])
        editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
       [surface.views/surface-label nil
                                    {:disabled?   editor-disabled?
                                     :label       label
                                     :loading?    (not data-received?)
                                     :placeholder placeholder}]))

(defn item-color-marker
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:colors (strings in vector)}
  ;
  ; @usage
  ;  [common/item-color-marker :my-editor {...}]
  [editor-id {:keys [colors]}]
  (if-let [data-received? @(a/subscribe [:item-editor/data-received? editor-id])]
          (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
               [elements/color-marker ::item-color-marker
                                      {:colors    colors
                                       :disabled? editor-disabled?
                                       :indent    {:left :m :top :xxs}
                                       :size      :l}])))

(defn item-editor-label-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:colors (strings in vector)
  ;   :label (metamorphic-content)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-editor-label-bar :my-editor {...}]
  [editor-id bar-props]
  (if-let [error-mode? @(a/subscribe [:item-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          [:<> [elements/horizontal-polarity ::item-editor-label-bar
                                             {:start-content [:<> [item-name-label         editor-id bar-props]
                                                                  [item-color-marker       editor-id bar-props]]
                                              :end-content   [:<> [revert-item-icon-button editor-id bar-props]
                                                                  [save-item-icon-button   editor-id bar-props]]}]]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-menu-item-props
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ; @param (map) item-props
  ;  {:change-keys (keywords in vector)(opt)
  ;   :label (metamorphic-content)
  ;   :view-id (keyword)}
  ;
  ; @usage
  ;  [common/item-editor-menu-item-props editor-id {...} {...} :my-view]
  ;
  ; @return (map)
  ;  {:active? (boolean)
  ;   :badge-color (keyword)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [editor-id _ {:keys [change-keys label view-id]}]
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id editor-id])
        changed? (if change-keys @(a/subscribe [:item-editor/form-changed? editor-id change-keys]))]
       {:active?     (= view-id current-view-id)
        :badge-color (if changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! editor-id view-id]}))

(defn item-editor-menu-bar
  ; A komponens használatához ne felejts el inicializálni egy gestures/view-handler
  ; kezelőt, az editor-id azonosítóval!
  ;
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:menu-items (maps in vector)
  ;    [{:change-keys (keywords in vector)(opt)
  ;      :label (metamorphic-content)
  ;      :view-id (keyword)}]}
  ;
  ; @usage
  ;  [common/item-editor-menu-bar :my-editor {...}]
  [editor-id {:keys [menu-items] :as bar-props}]
  ; XXX#5040
  (if-let [error-mode? @(a/subscribe [:item-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          (letfn [(f [menu-items menu-item] (conj menu-items (item-editor-menu-item-props editor-id bar-props menu-item)))]
                 [:<> (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
                           [elements/menu-bar ::item-editor-menu-bar {:disabled?  editor-disabled?
                                                                      :menu-items (reduce f [] menu-items)}])
                      [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]])))

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-action-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [_ {:keys [label on-click]}]
  [elements/button ::item-editor-action-button
                   {:color     :primary
                    :font-size :xs
                    :indent    {:vertical :xs :horizontal :xs}
                    :label     label
                    :on-click  on-click}])

(defn item-editor-action-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [common/item-editor-action-bar :my-editor {...}]
  [editor-id bar-props]
  ; XXX#5041
  (if-let [error-mode? @(a/subscribe [:item-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
               [elements/row ::item-editor-action-bar
                             {:content          [item-editor-action-button editor-id bar-props]
                              :disabled?        editor-disabled?
                              :horizontal-align :center}])))

;; -- Image-list components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-image
  ; @param (keyword) editor-id
  ; @param (map) list-props
  ; @param (string) image
  ;
  ; @usage
  ;  [common/item-editor-image :my-editor {...} "..."]
  [editor-id _ image]
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? editor-id])]
       [elements/thumbnail {:border-radius :s
                            :disabled?     editor-disabled?
                            :indent        {:left :xs :top :xxs}
                            :height        :2xl
                            :width         :4xl
                            :uri           image}]))

(defn item-editor-image-list
  ; @param (keyword) editor-id
  ; @param (map) list-props
  ;  {:images (strings in vector)
  ;   :no-images-label (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-editor-image-list :my-editor {...}]
  [editor-id {:keys [images no-images-label] :as list-props}]
  (letfn [(f [image-list image]
             (conj image-list [item-editor-image editor-id list-props image]))]
         (if (vector/nonempty? images)
             (reduce f [:div {:style {:display :flex :flex-wrap :wrap}}] images)
             [elements/label ::no-images-label
                             {:color     :highlight
                              :content   no-images-label
                              :font-size :xs
                              :indent    {:left :xs}}])))

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-ghost-view
  ; @param (keyword) editor-id
  ; @param (map) view-props
  ;  {:padding (string)(opt)}
  ;
  ; @usage
  ;  [common/item-editor-ghost-view :my-editor {...}]
  [_ {:keys [padding]}]
  [:div {:style {:padding padding :width "100%"}}
        [:div {:style {:display "flex" :width "100%" :grid-column-gap "24px"}}
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]]
        [:div {:style {:display "flex" :width "100%" :grid-column-gap "24px"}}
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]]
        [:div {:style {:display "flex" :width "100%" :grid-column-gap "24px"}}
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]
              [elements/ghost {:height :l :style {:flex-grow :1} :indent {:horizontal :xs}}]]])
