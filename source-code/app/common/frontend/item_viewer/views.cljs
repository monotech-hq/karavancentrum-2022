
(ns app.common.frontend.item-viewer.views
    (:require [app.common.frontend.item-editor.views :as item-editor.views]
              [app.common.frontend.surface.views     :as surface.views]
              [mid-fruits.vector                     :as vector]
              [x.app-core.api                        :as a]
              [x.app-elements.api                    :as elements]
              [x.app-locales.api                     :as locales]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-item-modified
  ; @param (keyword) viewer-id
  ; @param (map) info-props
  ;
  ; @usage
  ;  [common/item-viewer-item-modified :my-viewer {...}]
  [viewer-id _]
  (let [current-item   @(a/subscribe [:item-viewer/get-current-item viewer-id])
        added-at        (-> current-item :modified-at)
        user-first-name (-> current-item :modified-by :user-profile/first-name)
        user-last-name  (-> current-item :modified-by :user-profile/last-name)
        user-full-name @(a/subscribe [:locales/get-ordered-name user-first-name user-last-name])
        timestamp      @(a/subscribe [:activities/get-actual-timestamp added-at])
        modified        (str user-full-name ", " timestamp)]
       [elements/label ::item-viewer-item-modified
                       {:color :highlight
                        :content {:content :last-modified-n :replacements [modified]}
                        :font-size :xxs}]))

(defn item-viewer-item-created
  ; @param (keyword) viewer-id
  ; @param (map) info-props
  ;
  ; @usage
  ;  [common/item-viewer-item-created :my-viewer {...}]
  [viewer-id _]
  (let [current-item   @(a/subscribe [:item-viewer/get-current-item viewer-id])
        added-at        (-> current-item :added-at)
        user-first-name (-> current-item :added-by :user-profile/first-name)
        user-last-name  (-> current-item :added-by :user-profile/last-name)
        user-full-name @(a/subscribe [:locales/get-ordered-name user-first-name user-last-name])
        timestamp      @(a/subscribe [:activities/get-actual-timestamp added-at])
        created         (str user-full-name ", " timestamp)]
       [elements/label ::item-viewer-item-created
                       {:color :highlight
                        :content {:content :created-n :replacements [created]}
                        :font-size :xxs}]))

(defn item-viewer-item-info
  ; @param (keyword) viewer-id
  ; @param (map) info-props
  ;  {:indent (map)}
  ;
  ; @usage
  ;  [common/item-viewer-item-info :my-viewer {...}]
  [viewer-id {:keys [indent] :as info-props}]
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
       [elements/blank ::item-viewer-item-info
                       {:disabled? viewer-disabled?
                        :content   [:div {:style {:display :flex :flex-direction :column :align-items :center :justify-content :center}}
                                        [item-viewer-item-created  viewer-id info-props]
                                        [item-viewer-item-modified viewer-id info-props]]
                        :indent    indent}]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-menu-bar
  ; A komponens használatához ne felejts el inicializálni egy gestures/view-handler
  ; kezelőt, a viewer-id azonosítóval!
  ;
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :menu-items (maps in vector)
  ;    [{:label (metamorphic-content)}]}
  ;
  ; @usage
  ;  [common/item-viewer-menu-bar :my-viewer {...}]
  [viewer-id {:keys [disabled? menu-items] :as bar-props}]
  (letfn [(f [menu-items menu-item] (conj menu-items (item-editor.views/item-editor-menu-item-props viewer-id bar-props menu-item)))]
         [:<> [elements/menu-bar ::item-viewer-menu-bar {:disabled?  disabled?
                                                         :menu-items (reduce f [] menu-items)}]
              [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]]))

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-action-bar
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)}
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [common/item-viewer-action-bar :my-viewer {...}]
  [viewer-id bar-props]
  [elements/row ::item-viewer-action-bar
                {:content          [item-editor.views/item-editor-action-button viewer-id bar-props]
                 :horizontal-align :center}])

;; -- Image-list components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-image
  ; @param (keyword) viewer-id
  ; @param (map) list-props
  ;  {:disabled? (boolean)(opt)}
  ; @param (string) image
  ;
  ; @usage
  ;  [common/item-viewer-image :my-viewer {...} "..."]
  [viewer-id {:keys [disabled?]} image]
  [elements/thumbnail {:border-radius :s
                       :disabled?     disabled?
                       :indent        {:left :xs :top :xxs}
                       :height        :2xl
                       :width         :4xl
                       :uri           image}])

(defn item-viewer-image-list
  ; @param (keyword) viewer-id
  ; @param (map) list-props
  ;  {:disabled? (boolean)(opt)}
  ;   :images (strings in vector)
  ;   :no-images-label (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-viewer-image-list :my-viewer {...}]
  [viewer-id {:keys [images no-images-label] :as list-props}]
  ; XXX#5042
  (letfn [(f [image-list image]
             (conj image-list [item-viewer-image viewer-id list-props image]))]
         (if (vector/nonempty? images)
             (reduce f [:div {:style {:display :flex :flex-wrap :wrap}}] images)
             [elements/label ::no-images-label
                             {:color     :highlight
                              :content   no-images-label
                              :font-size :xs
                              :indent    {:left :xs}}])))

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-ghost-view
  ; @param (keyword) viewer-id
  ; @param (map) view-props
  ;
  ; @usage
  ;  [common/item-viewer-ghost-view :my-viewer {...}]
  [_ {:keys []}]
  [:div {:style {:padding "0 12px" :width "100%"}}
        [:div {:style {:padding-bottom "6px" :width "240px"}}
              [elements/ghost {:height :xl}]]
        [:div {:style {:display "flex" :grid-column-gap "12px" :padding-top "6px"}}
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]
              [:div {:style {:width "80px"}} [elements/ghost {:height :xs}]]]
        [:div {:style {:display "flex" :flex-direction "column" :width "100%" :grid-row-gap "24px" :padding-top "48px"}}
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l   :indent {}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l   :indent {}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :4xl :indent {}}]]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn delete-item-button
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)}
  ;
  ; @usage
  ;  [common/delete-item-button :my-viewer {...}]
  [viewer-id {:keys [disabled?]}]
  [elements/button ::delete-item-button
                   {:color       :warning
                    :disabled?   disabled?
                    :hover-color :highlight
                    :icon        :delete_outline
                    :label       :delete!
                    :on-click    [:item-viewer/delete-item! viewer-id]
                    :style       {:line-height "48px"}}])

(defn duplicate-item-button
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)}
  ;
  ; @usage
  ;  [common/duplicate-item-button :my-viewer {...}]
  [viewer-id {:keys [disabled?]}]
  [elements/button ::delete-item-button
                   {:color       :default
                    :disabled?   disabled?
                    :hover-color :highlight
                    :icon        :file_copy
                    :icon-family :material-icons-outlined
                    :indent      {:left :xs}
                    :label       :copy!
                    :on-click    [:item-viewer/duplicate-item! viewer-id]
                    :style       {:line-height "48px"}}])

(defn edit-item-button
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :edit-item-uri (string)}
  ;
  ; @usage
  ;  [common/edit-item-button :my-viewer {...}]
  [viewer-id {:keys [disabled? edit-item-uri]}]
  [elements/button ::edit-item-button
                   {:background-color "#5a4aff"
                    :color            "white"
                    :disabled?        disabled?
                    :icon             :edit
                    :indent           {:left :xs}
                    :label            :edit!
                    :on-click         [:router/go-to! edit-item-uri]
                    :style            {:line-height "48px"}}])

(defn item-viewer-control-bar
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :edit-item-uri (string)}
  ;
  ; @usage
  ;  [common/item-viewer-control-bar :my-viewer {...}]
  [viewer-id bar-props]
  [elements/horizontal-polarity ::item-viewer-control-bar
                                {:end-content [:<> [delete-item-button    viewer-id bar-props]
                                                   [duplicate-item-button viewer-id bar-props]
                                                   [edit-item-button      viewer-id bar-props]]}])
