
(ns app.common.frontend.item-editor.views
    (:require [app.common.frontend.surface.views :as surface.views]
              [mid-fruits.vector                 :as vector]
              [x.app-core.api                    :as a]
              [x.app-elements.api                :as elements]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-menu-item-props
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ; @param (map) item-props
  ;  {:change-keys (keywords in vector)(opt)
  ;   :label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-editor-menu-item-props editor-id {...} {...} :my-view]
  ;
  ; @return (map)
  ;  {:active? (boolean)
  ;   :badge-color (keyword)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [editor-id _ {:keys [change-keys label]}]
  (let [current-view @(a/subscribe [:gestures/get-current-view-id editor-id])
        changed? (if change-keys @(a/subscribe [:item-editor/form-changed? editor-id change-keys]))]
       {:active?     (= label current-view)
        :badge-color (if changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! editor-id label]}))

(defn item-editor-menu-bar
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
  ;  [common/item-editor-menu-bar :my-editor {...}]
  [editor-id {:keys [disabled? menu-items] :as bar-props}]
  (letfn [(f [menu-items menu-item] (conj menu-items (item-editor-menu-item-props editor-id bar-props menu-item)))]
         [:<> [elements/menu-bar ::item-editor-menu-bar {:disabled?  disabled?
                                                         :menu-items (reduce f [] menu-items)}]
              [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]]))

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-action-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [_ {:keys [disabled? label on-click]}]
  [elements/button ::item-editor-action-button
                   {:color     :primary
                    :disabled? disabled?
                    :font-size :xs
                    :indent    {:vertical :xs :horizontal :xs}
                    :label     label
                    :on-click  on-click}])

(defn item-editor-action-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [common/item-editor-action-bar :my-editor {...}]
  [editor-id bar-props]
  [elements/row ::item-editor-action-bar
                {:content          [item-editor-action-button editor-id bar-props]
                 :horizontal-align :center}])

;; -- Image-list components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-image
  ; @param (keyword) editor-id
  ; @param (map) list-props
  ;  {:disabled? (boolean)(opt)}
  ; @param (string) image
  ;
  ; @usage
  ;  [common/item-editor-image :my-editor {...} "..."]
  [editor-id {:keys [disabled?]} image]
  [elements/thumbnail {:border-radius :s
                       :disabled?     disabled?
                       :indent        {:left :xs :top :xxs}
                       :height        :2xl
                       :width         :4xl
                       :uri           image}])

(defn item-editor-image-list
  ; @param (keyword) editor-id
  ; @param (map) list-props
  ;  {:disabled? (boolean)(opt)}
  ;   :images (strings in vector)
  ;   :no-images-label (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-editor-image-list :my-editor {...}]
  [editor-id {:keys [images no-images-label] :as list-props}]
  ; XXX#5042
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
  ;
  ; @usage
  ;  [common/item-editor-ghost-view :my-editor {...}]
  [_ {:keys []}]
  [:div {:style {:padding "0 12px" :width "100%"}}
        [:div {:style {:padding-bottom "6px" :width "240px"}}
              [elements/ghost {:height :xl}]]
        [:div {:style {:display "flex" :grid-column-gap "12px" :padding-top "6px"}}
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]
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

(defn revert-item-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/revert-item-button :my-editor {...}]
  [editor-id {:keys [disabled?]}]
  (let [item-changed? @(a/subscribe [:item-editor/item-changed? editor-id])]
       [elements/button ::revert-item-button
                        {:disabled?   (or disabled? (not item-changed?))
                         :hover-color :highlight
                         :icon        :settings_backup_restore
                         :label       :revert!
                         :on-click    [:item-editor/revert-item! editor-id]
                         :style       {:line-height "48px"}}]))

(defn save-item-button
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/save-item-button :my-editor {...}]
  [editor-id {:keys [disabled?]}]
  [elements/button ::save-item-button
                   {:background-color "#5a4aff"
                    :color            "white"
                    :disabled?        disabled?
                    :icon             :save
                    :indent           {:left :xs}
                    :label            :save!
                    :on-click         [:item-editor/save-item! editor-id]
                    :style            {:line-height "48px"}}])

(defn item-editor-control-bar
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;
  ; @usage
  ;  [common/item-editor-control-bar :my-editor {...}]
  [editor-id bar-props]
  [elements/horizontal-polarity ::item-editor-control-bar
                                {:end-content [:<> [revert-item-button editor-id bar-props]
                                                   [save-item-button   editor-id bar-props]]}])
