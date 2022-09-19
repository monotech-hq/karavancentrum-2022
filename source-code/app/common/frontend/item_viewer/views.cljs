
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

;; -- Breadcrumbs components --------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-breadcrumbs
  ; @param (keyword) viewer-id
  ; @param (map) breadcrumbs-props
  ;  {:crumbs (maps in vector)}
  ;
  ; @usage
  ;  [common/item-viewer-breadcrumbs :my-viewer {...}]
  [viewer-id {:keys [crumbs]}]
  (if-let [error-mode? @(a/subscribe [:item-viewer/get-meta-item viewer-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-viewer felületen nem jelenik meg!
          (let [data-received?   @(a/subscribe [:item-viewer/data-received?   viewer-id])
                viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
               [surface.views/surface-breadcrumbs nil
                                                  {:crumbs    crumbs
                                                   :disabled? viewer-disabled?
                                                   :loading?  (not data-received?)}])))

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
  ;  {:label (metamorphic-content)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-name-label :my-viewer {...}]
  [viewer-id {:keys [label placeholder]}]
  (let [data-received?   @(a/subscribe [:item-viewer/data-received?   viewer-id])
        viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
       [surface.views/surface-label nil
                                    {:disabled?   viewer-disabled?
                                     :label       label
                                     :loading?    (not data-received?)
                                     :placeholder placeholder}]))

(defn item-viewer-label-bar
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:colors (strings in vector)(opt)
  ;   :edit-item-uri (string)
  ;   :label (metamorphic-content)(opt)
  ;   :placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-viewer-label-bar :my-viewer {...}]
  [viewer-id bar-props]
  (if-let [error-mode? @(a/subscribe [:item-viewer/get-meta-item viewer-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-viewer felületen nem jelenik meg!
          [:<> [elements/horizontal-polarity ::item-viewer-label-bar
                                             {:start-content [:<> [item-name-label            viewer-id bar-props]
                                                                  [item-color-marker          viewer-id bar-props]]
                                              :end-content   [:<> [delete-item-icon-button    viewer-id bar-props]
                                                                  [duplicate-item-icon-button viewer-id bar-props]
                                                                  [edit-item-icon-button      viewer-id bar-props]]}]]))

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-menu-bar
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
  [viewer-id {:keys [menu-items] :as bar-props}]
  ; XXX#5040
  (if-let [error-mode? @(a/subscribe [:item-viewer/get-meta-item viewer-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-viewer felületen nem jelenik meg!
          (letfn [(f [menu-items menu-item] (conj menu-items (item-editor.views/item-editor-menu-item-props viewer-id bar-props menu-item)))]
                 [:<> (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
                           [elements/menu-bar ::item-viewer-menu-bar {:disabled?  viewer-disabled?
                                                                      :menu-items (reduce f [] menu-items)}])
                      [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]])))

;; -- Action-bar components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-action-bar
  ; @param (keyword) viewer-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [common/item-viewer-action-bar :my-viewer {...}]
  [viewer-id bar-props]
  ; XXX#5041
  (if-let [error-mode? @(a/subscribe [:item-viewer/get-meta-item viewer-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-viewer felületen nem jelenik meg!
          (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
               [elements/row ::item-viewer-action-bar
                             {:content          [item-editor.views/item-editor-action-button viewer-id bar-props]
                              :disabled?        viewer-disabled?
                              :horizontal-align :center}])))

;; -- Image-list components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-viewer-image
  ; @param (keyword) viewer-id
  ; @param (map) list-props
  ; @param (string) image
  ;
  ; @usage
  ;  [common/item-viewer-image :my-viewer {...} "..."]
  [viewer-id _ image]
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? viewer-id])]
       [elements/thumbnail {:border-radius :s
                            :disabled?     viewer-disabled?
                            :indent        {:left :xs :top :xxs}
                            :height        :2xl
                            :width         :4xl
                            :uri           image}]))

(defn item-viewer-image-list
  ; @param (keyword) viewer-id
  ; @param (map) list-props
  ;  {:images (strings in vector)
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
  ;  {:padding (string)(opt)}
  ;
  ; @usage
  ;  [common/item-viewer-ghost-view :my-viewer {...}]
  [_ {:keys [padding]}]
  [:div {:style {:width "100%" :padding padding}}
        [elements/ghost {:height :l :style {:flex-grow :1}                 :indent {}}]
        [elements/ghost {:height :l :style {:flex-grow :1}                 :indent {:top :xs}}]
        [elements/ghost {           :style {:flex-grow :1 :height "120px"} :indent {:top :xs}}]])
