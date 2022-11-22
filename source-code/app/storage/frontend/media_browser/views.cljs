
(ns app.storage.frontend.media-browser.views
    (:require [app.common.frontend.api                    :as common]
              [app.components.frontend.api                :as components]
              [app.storage.frontend.core.config           :as core.config]
              [app.storage.frontend.media-browser.helpers :as media-browser.helpers]
              [elements.api                               :as elements]
              [engines.item-browser.api                   :as item-browser]
              [format.api                                 :as format]
              [io.api                                     :as io]
              [layouts.surface-a.api                      :as surface-a]
              [keyword.api                                :as keyword]
              [re-frame.api                               :as r]
              [x.components.api                           :as x.components]
              [x.media.api                                :as x.media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (if-let [first-data-received? @(r/subscribe [:item-browser/first-data-received? :storage.media-browser])]
          [common/item-lister-download-info :storage.media-browser {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- directory-list-item
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  ; @param (integer) item-dex
  ; @param (map) media-item
  [_ _ item-dex {:keys [alias size id items modified-at]}]
  (let [timestamp  @(r/subscribe [:x.activities/get-actual-timestamp modified-at])
        item-count  (x.components/content {:content :n-items :replacements [(count items)]})
        size        (-> size io/B->MB format/decimals (str " MB"))
        icon-family (if (empty? items) :material-icons-outlined :material-icons-filled)]
       [components/item-list-row {:cells [[components/list-item-gap       {:width 12}]
                                          [components/list-item-thumbnail {:icon :folder :icon-family icon-family}]
                                          [components/list-item-gap       {:width 12}]
                                          [components/list-item-cell      {:rows [{:content alias :placeholder :unnamed-directory}]}]
                                          [components/list-item-gap       {:width 12}]
                                          [components/list-item-cell      {:rows [{:content size :font-size :xs :color :muted}] :width 100}]
                                          [components/list-item-gap       {:width 12}]
                                          [components/list-item-cell      {:rows [{:content timestamp :font-size :xs :color :muted}] :width 100}]
                                          [components/list-item-gap       {:width 6}]
                                          [components/list-item-button    {:label :open! :width 100 :on-click [:item-browser/browse-item! :storage.media-browser id]}]
                                          [components/list-item-gap       {:width 6}]]
                                  :border (if (not= item-dex 0) :top)}]))

(defn- file-list-item
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  ; @param (integer) item-dex
  ; @param (map) media-item
  [_ _ item-dex {:keys [alias id modified-at filename size] :as media-item}]
  (let [timestamp @(r/subscribe [:x.activities/get-actual-timestamp modified-at])
        size       (-> size io/B->MB format/decimals (str " MB"))]
       [components/item-list-row {:cells [[components/list-item-gap    {:width 12}]
                                          ; XXX#6690 (source-code/app/storage/media-browser/views.cljs)
                                          (cond (io/filename->audio? alias)
                                                [components/list-item-thumbnail {:icon :audio_file :icon-family :material-icons-outlined}]
                                                (io/filename->image? alias)
                                                [components/list-item-thumbnail {:thumbnail (x.media/filename->media-thumbnail-uri filename)}]
                                                (io/filename->text?  alias)
                                                [components/list-item-thumbnail {:icon :insert_drive_file :icon-family :material-icons-outlined}]
                                                (io/filename->video? alias)
                                                [components/list-item-thumbnail {:icon :video_file :icon-family :material-icons-outlined}]
                                                :else
                                                [components/list-item-thumbnail {:icon :insert_drive_file :icon-family :material-icons-outlined}])
                                          [components/list-item-gap    {:width 12}]
                                          [components/list-item-cell   {:rows [{:content alias :placeholder :unnamed-file}]}]
                                          [components/list-item-gap    {:width 12}]
                                          [components/list-item-cell   {:rows [{:content size :font-size :xs :color :muted}] :width 100}]
                                          [components/list-item-gap    {:width 12}]
                                          [components/list-item-cell   {:rows [{:content timestamp :font-size :xs :color :muted}] :width 100}]
                                          [components/list-item-gap    {:width 6}]
                                          [components/list-item-button {:icon :more_horiz :width 100 :on-click [:storage.media-menu/render-file-menu! media-item]}]
                                          [components/list-item-gap    {:width 6}]]
                                  :border (if (not= item-dex 0) :top)}]))

(defn- media-list-item
  ; @param (keyword) browser-id
  ; @param (map) browser-props
  ; @param (integer) item-dex
  ; @param (map) media-item
  [browser-id browser-props item-dex {:keys [mime-type] :as media-item}]
  (case mime-type "storage/directory" [directory-list-item browser-id browser-props item-dex media-item]
                                      [file-list-item      browser-id browser-props item-dex media-item]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- upload-files-button
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [elements/button ::upload-files-button
                        {:border-radius :s
                         :disabled?     browser-disabled?
                         :font-size     :xs
                         :hover-color   :highlight
                         :icon          :upload_file
                         :indent        {:right :xs :top :xs}
                         :label         :upload-file!
                         :on-click      [:storage.media-browser/upload-files!]}]))

(defn- create-folder-button
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [elements/button ::create-folder-button
                        {:border-radius :s
                         :disabled?     browser-disabled?
                         :font-size     :xs
                         :hover-color   :highlight
                         :indent        {:right :xs :top :xs}
                         :on-click      [:storage.media-browser/create-directory!]
                         :icon          :create_new_folder
                         :icon-family   :material-icons-outlined
                         :label         :create-directory!}]))

(defn- go-home-icon-button
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])
        at-home?          @(r/subscribe [:item-browser/at-home?          :storage.media-browser])
        error-mode?       @(r/subscribe [:item-browser/get-meta-item     :storage.media-browser :error-mode?])]
       [elements/icon-button ::go-home-icon-button
                             {:disabled?     (or browser-disabled? (and at-home? (not error-mode?)))
                              :border-radius :s
                              :hover-color   :highlight
                              :indent        {:top :xxs}
                              :on-click      [:item-browser/go-home! :storage.media-browser]
                              :preset        :home}]))

(defn- go-up-icon-button
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])
        at-home?          @(r/subscribe [:item-browser/at-home?          :storage.media-browser])]
       [elements/icon-button ::go-up-icon-button
                             {:disabled?     (or browser-disabled? at-home?)
                              :border-radius :s
                              :hover-color   :highlight
                              :indent        {:top :xxs}
                              :on-click      [:item-browser/go-up! :storage.media-browser]
                              :preset        :back}]))

(defn- control-bar
  []
  [elements/horizontal-polarity ::control-bar
                                {:start-content [:<> [go-up-icon-button]
                                                     [go-home-icon-button]]
                                 :end-content   [:<> [create-folder-button]
                                                     [upload-files-button]]}])

(defn- media-list-header
  []
  (let [current-order-by @(r/subscribe [:item-browser/get-current-order-by :storage.media-browser])]
       [components/item-list-header ::vehicle-list-header
                                    {:cells [{:width 12}
                                             {:width 84}
                                             {:width 12}
                                             {:label :name :order-by-key :name
                                              :on-click [:item-browser/order-items! :rental-vehicles.media-browser :name]}
                                             {:width 12}
                                             {:label :size :width 100 :order-by-key :size
                                              :on-click [:item-browser/order-items! :rental-vehicles.media-browser :size]}
                                             {:width 12}
                                             {:label :modified :width 100 :order-by-key :modified-at
                                              :on-click [:item-browser/order-items! :rental-vehicles.media-browser :modified-at]}
                                             {:width 12}
                                             {:width 100}
                                             {:width 12}]
                                     :border :bottom
                                     :order-by current-order-by}]))

(defn- media-browser
  []
  [common/item-browser :storage.media-browser
                       {:auto-title?       true
                        :default-order-by  :modified-at/descending
                        :default-item-id   core.config/ROOT-DIRECTORY-ID
                        :item-path         [:storage :media-browser/browsed-item]
                        :items-path        [:storage :media-browser/downloaded-items]
                        :item-list-header  #'media-list-header
                        :list-item-element #'media-list-item
                        :items-key         :items
                        :label-key         :alias
                        :path-key          :path}])

(defn- body
  []
  [components/surface-box ::body
                          {:content [:<> [control-bar]
                                         [media-browser]
                                         [elements/horizontal-separator {:height :xxs}]]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- directory-info
  []
  (let [size  @(r/subscribe [:x.db/get-item [:storage :media-browser/browsed-item :size]])
        items @(r/subscribe [:x.db/get-item [:storage :media-browser/browsed-item :items]])
        directory-info (str (-> size io/B->MB format/decimals (str " MB\u00A0\u00A0\u00A0|\u00A0\u00A0\u00A0"))
                            (x.components/content {:content :n-items :replacements [(count items)]}))]
       [components/surface-description ::directory-info
                                       {:content          directory-info
                                        :indent           {:top :m :right :xs}
                                        :horizontal-align :right}]))

(defn- search-field
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [common/item-browser-search-field :storage.media-browser
                                         {:disabled?   browser-disabled?
                                          :placeholder :search-in-the-directory
                                          :search-keys [:alias]}]))

(defn- search-description
  []
  (let [lister-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [common/item-browser-search-description :storage.media-browser
                                               {:disabled? lister-disabled?}]))

(defn- breadcrumbs
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [components/surface-breadcrumbs ::breadcrumbs
                                        {:crumbs [{:label :app-home :route "/@app-home"}
                                                  {:label :storage}]
                                         :disabled? browser-disabled?}]))

(defn- label
  []
  (let [browser-disabled? @(r/subscribe [:item-browser/browser-disabled? :storage.media-browser])
        directory-alias @(r/subscribe [:item-browser/get-current-item-label :storage.media-browser])]
       [components/surface-label ::label
                                 {:disabled? browser-disabled?
                                  :label     directory-alias}]))

(defn- header
  []
  (if-let [first-data-received? @(r/subscribe [:item-browser/first-data-received? :storage.media-browser])]
          [:<> [label]
               [breadcrumbs]
               [search-field]
               [:div {:style {:display :flex :justify-content :space-between}}
                     [search-description]
                     [directory-info]]]
          [components/ghost-view {:breadcrumb-count 2 :layout :box-surface-header}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) surface-id
  [_]
  [:<> [header]
       [body]
       [footer]])

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
