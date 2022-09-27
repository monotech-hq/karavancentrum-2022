
(ns app.common.frontend.file-editor.views
    (:require [app.common.frontend.item-editor.views :as item-editor.views]
              [app.common.frontend.surface.views     :as surface.views]
              [x.app-core.api                        :as a]
              [x.app-elements.api                    :as elements]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn file-editor-menu-item-props
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ; @param (map) item-props
  ;  {:change-keys (keywords in vector)(opt)
  ;   :label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/file-editor-menu-item-props :my-editor {...} {...}]
  ;
  ; @return (map)
  ;  {:active? (boolean)
  ;   :badge-color (keyword)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [editor-id _ {:keys [change-keys label]}]
  (let [current-view @(a/subscribe [:gestures/get-current-view-id editor-id])
        changed? (if change-keys @(a/subscribe [:file-editor/form-changed? editor-id change-keys]))]
       {:active?     (= label current-view)
        :badge-color (if changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! editor-id label]}))

(defn file-editor-menu-bar
  ; A komponens használatához ne felejts el inicializálni egy gestures/view-handler
  ; kezelőt, az editor-id azonosítóval!
  ;
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :menu-items (maps in vector)
  ;    [{:change-keys (keywords in vector)(opt)
  ;      :label (metamorphic-content)}]}
  ;
  ; @usage
  ;  [common/file-editor-menu-bar :my-editor {...}]
  [editor-id {:keys [disabled? menu-items] :as bar-props}]
  (letfn [(f [menu-items menu-item] (conj menu-items (file-editor-menu-item-props editor-id bar-props menu-item)))]
         [:<> (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? editor-id])]
                   [elements/menu-bar ::file-editor-menu-bar {:disabled?  disabled?
                                                              :menu-items (reduce f [] menu-items)}])
              [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]]))

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn file-editor-action-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [common/file-editor-action-bar :my-editor {...}]
  [editor-id bar-props]
  [item-editor.views/item-editor-action-bar editor-id bar-props])

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn file-editor-ghost-view
  ; @param (keyword) editor-id
  ; @param (map) view-props
  ;
  ; @usage
  ;  [common/file-editor-ghost-view :my-editor {...}]
  [editor-id view-props]
  [:div {:style {:padding "0 12px" :width "100%"}}
        [:div {:style {:padding-bottom "6px" :width "240px"}}
              [elements/ghost {:height :xl}]]
        [:div {:style {:display "flex" :grid-column-gap "12px" :padding-top "6px"}}
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]]
        [:div {:style {:display "flex" :width "100%" :grid-column-gap "24px" :padding-top "48px"}}
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]]
        [:div {:style {:display "flex" :width "100%" :grid-column-gap "24px"}}
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]]
        [:div {:style {:display "flex" :width "100%" :grid-column-gap "24px"}}
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {:horizontal :xs}}]]]])


;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn revert-content-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/revert-content-button :my-editor {...}]
  [editor-id {:keys [disabled?]}]
  (let [content-changed? @(a/subscribe [:file-editor/content-changed? editor-id])]
       [elements/button ::revert-content-button
                        {:disabled?   (or disabled? (not content-changed?))
                         :hover-color :highlight
                         :icon        :settings_backup_restore
                         :label       :revert!
                         :on-click    [:file-editor/revert-content! editor-id]
                         :style       {:line-height "48px" :padding "0 24px 0 18px"}}]))

(defn save-content-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/save-content-button :my-editor {...}]
  [editor-id {:keys [disabled?]}]
  [elements/button ::save-content-button
                   {:background-color "#5a4aff"
                    :color            "white"
                    :disabled?        disabled?
                    :icon             :save
                    :indent           {:vertical :xs}
                    :label            :save!
                    :on-click         [:file-editor/save-content! editor-id]
                    :style            {:line-height "48px" :padding "0 24px 0 18px"}}])

(defn file-editor-control-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/file-editor-control-bar :my-editor {...}]
  [editor-id bar-props]
  [elements/horizontal-polarity ::file-editor-control-bar
                                {:end-content [:<> [revert-content-button editor-id bar-props]
                                                   [save-content-button   editor-id bar-props]]}])
