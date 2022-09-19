
(ns app.common.frontend.config-editor.views
    (:require [app.common.frontend.item-editor.views :as item-editor.views]
              [app.common.frontend.surface.views     :as surface.views]
              [x.app-core.api                        :as a]
              [x.app-elements.api                    :as elements]))

;; -- Breadcrumbs components --------------------------------------------------
;; ----------------------------------------------------------------------------

(defn config-editor-breadcrumbs
  ; @param (keyword) editor-id
  ; @param (map) breadcrumbs-props
  ;  {:crumbs (maps in vector)}
  ;
  ; @usage
  ;  [common/config-editor-breadcrumbs :my-editor {...}]
  [editor-id {:keys [crumbs]}]
  (if-let [error-mode? @(a/subscribe [:config-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          (let [data-received?   @(a/subscribe [:config-editor/data-received?   editor-id])
                editor-disabled? @(a/subscribe [:config-editor/editor-disabled? editor-id])]
               [surface.views/surface-breadcrumbs nil
                                                  {:crumbs    crumbs
                                                   :disabled? editor-disabled?
                                                   :loading?  (not data-received?)}])))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn revert-config-icon-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/revert-config-icon-button :my-editor {...}]
  [editor-id _]
  (if-let [data-received? @(a/subscribe [:config-editor/data-received? editor-id])]
          (let [editor-disabled? @(a/subscribe [:config-editor/editor-disabled? editor-id])
                config-changed?  @(a/subscribe [:config-editor/config-changed?  editor-id])]
               [elements/icon-button ::revert-config-icon-button
                                     {:color       :default
                                      :disabled?   (or editor-disabled? (not config-changed?))
                                      :hover-color :highlight
                                      :icon        :settings_backup_restore
                                      :on-click    [:config-editor/revert-config! editor-id]}])))

(defn save-config-icon-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/save-config-icon-button :my-editor {...}]
  [editor-id _]
  (if-let [data-received? @(a/subscribe [:config-editor/data-received? editor-id])]
          (let [editor-disabled? @(a/subscribe [:config-editor/editor-disabled? editor-id])]
               [elements/icon-button ::save-config-icon-button
                                     {:color       :primary
                                      :disabled?   editor-disabled?
                                      :hover-color :highlight
                                      :icon        :save
                                      :on-click    [:config-editor/save-config! editor-id]}])))

(defn config-label
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/config-label :my-editor {...}]
  [editor-id {:keys [label]}]
  (let [data-received?   @(a/subscribe [:config-editor/data-received?   editor-id])
        editor-disabled? @(a/subscribe [:config-editor/editor-disabled? editor-id])]
       [surface.views/surface-label nil
                                    {:disabled? editor-disabled?
                                     :label     label
                                     :loading?  (not data-received?)}]))

(defn config-editor-label-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/config-editor-label-bar :my-editor {...}]
  [editor-id bar-props]
  (if-let [error-mode? @(a/subscribe [:item-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          [:<> [elements/horizontal-polarity ::item-editor-label-bar
                                             {:start-content [:<> [config-label              editor-id bar-props]]
                                              :end-content   [:<> [revert-config-icon-button editor-id bar-props]
                                                                  [save-config-icon-button   editor-id bar-props]]}]]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn config-editor-menu-item-props
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ; @param (map) item-props
  ;  {:change-keys (keywords in vector)(opt)
  ;   :label (metamorphic-content)
  ;   :view-id (keyword)}
  ;
  ; @usage
  ;  [common/config-editor-menu-item-props editor-id {...} {...} :my-view]
  ;
  ; @return (map)
  ;  {:active? (boolean)
  ;   :badge-color (keyword)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [editor-id _ {:keys [change-keys label view-id]}]
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id editor-id])
        changed? (if change-keys @(a/subscribe [:config-editor/form-changed? editor-id change-keys]))]
       {:active?     (= view-id current-view-id)
        :badge-color (if changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! editor-id view-id]}))

(defn config-editor-menu-bar
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
  ;  [common/config-editor-menu-bar :my-editor {...}]
  [editor-id {:keys [menu-items] :as bar-props}]
  ; XXX#5040
  (if-let [error-mode? @(a/subscribe [:config-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú config-editor felületen nem jelenik meg!
          (letfn [(f [menu-items menu-item] (conj menu-items (config-editor-menu-item-props editor-id bar-props menu-item)))]
                 [:<> (let [editor-disabled? @(a/subscribe [:config-editor/editor-disabled? editor-id])]
                           [elements/menu-bar ::config-editor-menu-bar {:disabled?  editor-disabled?
                                                                        :menu-items (reduce f [] menu-items)}])
                      [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]])))

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn config-editor-action-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [common/config-editor-action-bar :my-editor {...}]
  [editor-id bar-props]
  ; XXX#5041
  (if-let [error-mode? @(a/subscribe [:config-editor/get-meta-item editor-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú config-editor felületen nem jelenik meg!
          (let [editor-disabled? @(a/subscribe [:config-editor/editor-disabled? editor-id])]
               [elements/row ::config-editor-action-bar
                             {:content          [item-editor.views/item-editor-action-button editor-id bar-props]
                              :disabled?        editor-disabled?
                              :horizontal-align :center}])))

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn config-editor-ghost-view
  ; @param (keyword) editor-id
  ; @param (map) view-props
  ;  {:padding (string)(opt)}
  ;
  ; @usage
  ;  [common/config-editor-ghost-view :my-editor {...}]
  [_ {:keys [padding]}]
  [:div {:style {:padding padding :width "100%"}}
        [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
        [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
        [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
        [elements/ghost {           :indent {:top :xs :vertical :xs} :style {:height "96px"}}]])
