
(ns app.common.frontend.viewer.views
    (:require [app.common.frontend.editor.views  :as editor.views]
              [app.common.frontend.surface.views :as surface.views]
              [x.app-core.api                    :as a]
              [x.app-elements.api                :as elements]))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn delete-item-icon-button
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/delete-item-icon-button :my-viewer {...}]
  [viewer-id _]
  (if-let [data-received? @(a/subscribe [:item-viewer/data-received? viewer-id])]
          (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
               [elements/icon-button ::delete-item-icon-button
                                     {:color       :warning
                                      :disabled?   viewer-disabled?
                                      :hover-color :highlight
                                      :icon        :delete_outline
                                      :on-click    [:item-viewer/delete-item! viewer-id]}])))

(defn duplicate-item-icon-button
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/duplicate-item-icon-button :my-viewer {...}]
  [viewer-id _]
  (if-let [data-received? @(a/subscribe [:item-viewer/data-received? viewer-id])]
          (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
               [elements/icon-button ::delete-item-icon-button
                                     {:color       :default
                                      :disabled?   viewer-disabled?
                                      :hover-color :highlight
                                      :icon        :file_copy
                                      :icon-family :material-icons-outlined
                                      :on-click    [:item-viewer/duplicate-item! viewer-id]}])))

(defn edit-item-icon-button
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:edit-item-uri (string)}
  ;
  ; @usage
  ;  [common/edit-item-icon-button :my-viewer {...}]
  [viewer-id {:keys [edit-item-uri]}]
  (if-let [data-received? @(a/subscribe [:item-viewer/data-received? viewer-id])]
          (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
               [elements/icon-button ::edit-item-icon-button
                                     {:color       :default
                                      :disabled?   viewer-disabled?
                                      :hover-color :highlight
                                      :icon        :edit
                                      :on-click    [:router/go-to! edit-item-uri]}])))

(defn item-color-marker
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:colors (strings in vector)}
  ;
  ; @usage
  ;  [common/item-color-marker :my-viewer {...}]
  [viewer-id {:keys [colors]}]
  (if-let [data-received? @(a/subscribe [:item-viewer/data-received? viewer-id])]
          (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
               [elements/color-marker ::item-color-marker
                                      {:colors    colors
                                       :disabled? viewer-disabled?
                                       :indent    {:left :xs :top :xxs}
                                       :size      :l}])))

(defn item-name-label
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:name (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-name-label :my-viewer {...}]
  [viewer-id {:keys [name]}]
  (let [data-received?   @(a/subscribe [:item-viewer/data-received?   viewer-id])
        viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
       [surface.views/surface-label viewer-id
                                    {:disabled?   viewer-disabled?
                                     :label       name
                                     :loading?    (not data-received?)}]))

(defn item-modified-at-label
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/item-modified-at-label :my-viewer {...}]
  [viewer-id _]
  (let [data-received?   @(a/subscribe [:item-viewer/data-received?               viewer-id])
        viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled?             viewer-id])
        item-modified-at @(a/subscribe [:item-viewer/get-current-item-modified-at viewer-id])]
      [surface.views/surface-description viewer-id
                                         {:description {:content :last-modified-at-n :replacements [item-modified-at]}
                                          :disabled?   viewer-disabled?
                                          :loading?    (not data-received?)}]))

(defn item-viewer-label-bar
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:colors (strings in vector)(opt)
  ;   :edit-item-uri (string)
  ;   :name (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-viewer-label-bar :my-viewer {...}]
  [viewer-id bar-props]
  (if-let [error-mode? @(a/subscribe [:item-viewer/get-meta-item viewer-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-editor felületen nem jelenik meg!
          [:<> [elements/horizontal-polarity ::item-viewer-label-bar
                                             {:start-content [:<> [item-name-label            viewer-id bar-props]
                                                                  [item-color-marker          viewer-id bar-props]]
                                              :end-content   [:<> [delete-item-icon-button    viewer-id bar-props]
                                                                  [duplicate-item-icon-button viewer-id bar-props]
                                                                  [edit-item-icon-button      viewer-id bar-props]]}]
               [item-modified-at-label viewer-id bar-props]]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-menu-bar
  ; Az item-viewer-action-bar komponens az item-editor-action-bar komponensre épül.
  ; A komponens további paraméterezését az item-editor-action-bar komponens leírásában találod.
  ;
  ; A komponens használatához ne felejts el inicializálni egy gestures/view-handler
  ; kezelőt, a viewer-id azonosítóval!
  ;
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:menu-items (maps in vector)
  ;    [{:label (metamorphic-content)
  ;      :view-id (keyword)}]}
  ;
  ; @usage
  ;  [common/item-viewer-menu-bar :my-viewer {...}]
  [viewer-id bar-props]
  [editor.views/item-editor-menu-bar viewer-id bar-props])

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-action-bar
  ; Az item-viewer-action-bar komponens az item-editor-action-bar komponensre épül.
  ; A komponens további paraméterezését az item-editor-action-bar komponens leírásában találod.
  ;
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/item-viewer-action-bar :my-viewer {...}]
  [viewer-id bar-props]
  [editor.views/item-editor-action-bar viewer-id bar-props])

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-ghost-view
  ; @param (keyword) viewer-id
  ; @param (map) view-props
  ;  {:padding (string)(opt)}
  ;
  ; @usage
  ;  [common/item-viewer-ghost-view :my-viewer {...}]
  [_ {:keys [padding]}]
  [:div {:style {:width "100%" :padding padding}}
        [elements/ghost {:height :l :style {:flex-grow :1}                 :indent {}}]
        [elements/ghost {:height :l :style {:flex-grow :1}                 :indent {:top :xs}}]
        [elements/ghost {           :style {:flex-grow :1 :height "120px"} :indent {:top :xs}}]])
