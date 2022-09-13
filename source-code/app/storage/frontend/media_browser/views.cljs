
(ns app.storage.frontend.media-browser.views
    (:require [app.common.frontend.api                    :as common]
              [app.storage.frontend.core.config           :as core.config]
              [app.storage.frontend.media-browser.helpers :as media-browser.helpers]
              [layouts.surface-a.api                      :as surface-a]
              [mid-fruits.format                          :as format]
              [mid-fruits.keyword                         :as keyword]
              [mid-fruits.io                              :as io]
              [plugins.item-browser.api                   :as item-browser]
              [x.app-components.api                       :as components]
              [x.app-core.api                             :as a :refer [r]]
              [x.app-elements.api                         :as elements]
              [x.app-media.api                            :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- search-block
  []
  [common/item-browser-search-block :storage.media-browser
                                    {:field-placeholder :search-in-the-directory}])

(defn- breadcrumbs
  []
  (if-let [error-mode? @(a/subscribe [:item-browser/get-meta-item :storage.media-browser :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-browser felületen nem jelenik meg!
          (let [data-received?    @(a/subscribe [:item-browser/data-received?    :storage.media-browser])
                browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
               [common/surface-breadcrumbs :storage.media-lister/view
                                           {:crumbs [{:label :app-home
                                                      :route "/@app-home"}
                                                     {:label :storage}]
                                            :disabled? browser-disabled?
                                            :loading?  (not data-received?)}])))

(defn- label-bar
  []
  (let [directory-alias @(a/subscribe [:item-browser/get-current-item-label :storage.media-browser])
        size  @(a/subscribe [:db/get-item [:storage :media-browser/browsed-item :size]])
        items @(a/subscribe [:db/get-item [:storage :media-browser/browsed-item :items]])
        size   (str (-> size io/B->MB format/decimals (str " MB\u00A0\u00A0\u00A0|\u00A0\u00A0\u00A0"))
                    (components/content {:content :n-items :replacements [(count items)]}))]
       [common/item-browser-label-bar :storage.media-browser
                                      {:description size
                                       :label       directory-alias}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn directory-item-structure
  [browser-id item-dex {:keys [alias size id items modified-at]}]
  (let [timestamp  @(a/subscribe [:activities/get-actual-timestamp modified-at])
        item-count  (components/content {:content :n-items :replacements [(count items)]})
        size        (-> size io/B->MB format/decimals (str " MB"))
        icon-family (if (empty? items) :material-icons-outlined :material-icons-filled)]
       [common/list-item-structure browser-id item-dex
                                   {:cells [[common/list-item-thumbnail-icon browser-id item-dex {:icon :folder :icon-family icon-family}]
                                            [common/list-item-label          browser-id item-dex {:content alias :stretch? true}]
                                            [common/list-item-details        browser-id item-dex {:contents [size item-count]      :width "160px"}]
                                            [common/list-item-detail         browser-id item-dex {:content timestamp :width "160px"}]
                                            [common/list-item-end-icon       browser-id item-dex {:icon    :more_vert}]]}]))

(defn directory-item
  [browser-id item-dex {:keys [id] :as directory-item}]
  [elements/toggle {:content        [directory-item-structure browser-id item-dex directory-item]
                    :hover-color    :highlight
                    :on-click       [:item-browser/browse-item! :storage.media-browser id]
                    :on-right-click [:storage.media-browser/render-directory-menu! directory-item]}])

(defn file-item-structure
  [browser-id item-dex {:keys [alias id modified-at filename size] :as file-item}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])
        size       (-> size io/B->MB format/decimals (str " MB"))]
       [common/list-item-structure browser-id item-dex
                                   {:cells [(if (io/filename->image? alias)
                                                (let [thumbnail (media/filename->media-thumbnail-uri filename)]
                                                     [common/list-item-thumbnail browser-id item-dex {:thumbnail thumbnail}])
                                                [common/list-item-thumbnail-icon browser-id item-dex {:icon :insert_drive_file :icon-family :material-icons-outlined}])
                                            [common/list-item-label     browser-id item-dex {:content   alias     :stretch? true}]
                                            [common/list-item-detail    browser-id item-dex {:content   size      :width "160px"}]
                                            [common/list-item-detail    browser-id item-dex {:content   timestamp :width "160px"}]
                                            [common/list-item-end-icon  browser-id item-dex {:icon      :more_vert}]]}]))

(defn file-item
  [browser-id item-dex file-item]
  [elements/toggle {:content        [file-item-structure browser-id item-dex file-item]
                    :hover-color    :highlight
                    :on-click       [:storage.media-browser/render-file-menu! file-item]
                    :on-right-click [:storage.media-browser/render-file-menu! file-item]}])

(defn media-item
  [browser-id item-dex {:keys [mime-type] :as media-item}]
  (case mime-type "storage/directory" [directory-item browser-id item-dex media-item]
                                      [file-item      browser-id item-dex media-item]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-lister-ghost-view :storage.media-browser
                                 {:padding "0 12px"}])

(defn media-browser
  []
  [item-browser/body :storage.media-browser
                     {:auto-title?      true
                      :default-item-id   core.config/ROOT-DIRECTORY-ID
                      :default-order-by :modified-at/descending
                      :ghost-element    #'ghost-view
                      :item-path        [:storage :media-browser/browsed-item]
                      :items-path       [:storage :media-browser/downloaded-items]
                      :items-key        :items
                      :label-key        :alias
                      :list-element     #'media-item
                      :path-key         :path}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn upload-files-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [elements/icon-button ::upload-files-icon-button
                             {:disabled?     browser-disabled?
                              :border-radius :s
                              :hover-color   :highlight
                              :indent        {:top :xxs}
                              :on-click      [:storage.media-browser/upload-files!]
                              :icon          :upload_file}]))

(defn create-folder-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-browser])]
       [elements/icon-button ::create-folder-icon-button
                             {:disabled?     browser-disabled?
                              :border-radius :s
                              :hover-color   :highlight
                              :indent        {:top :xxs}
                              :on-click      [:storage.media-browser/create-directory!]
                              :icon          :create_new_folder}]))

(defn go-home-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-browser])
        at-home?          @(a/subscribe [:item-browser/at-home?          :storage.media-browser])
        error-mode?       @(a/subscribe [:item-browser/get-meta-item     :storage.media-browser :error-mode?])]
       [elements/icon-button ::go-home-icon-button
                             {:disabled?     (or browser-disabled? (and at-home? (not error-mode?)))
                              :border-radius :s
                              :hover-color   :highlight
                              :indent        {:top :xxs}
                              :on-click      [:item-browser/go-home! :storage.media-browser]
                              :preset        :home}]))

(defn go-up-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-browser])
        at-home?          @(a/subscribe [:item-browser/at-home?          :storage.media-browser])]
       [elements/icon-button ::go-up-icon-button
                             {:disabled?     (or browser-disabled? at-home?)
                              :border-radius :s
                              :hover-color   :highlight
                              :indent        {:top :xxs}
                              :on-click      [:item-browser/go-up! :storage.media-browser]
                              :preset        :back}]))


(defn control-bar
  []
  [elements/horizontal-polarity ::control-bar
                                {:start-content [:<> [go-up-icon-button]
                                                     [go-home-icon-button]]
                                 :end-content   [:<> [create-folder-icon-button]
                                                     [upload-files-icon-button]]}])

(defn media-browser-header
  []
  [common/item-lister-header :storage.media-browser
                             {:cells [[common/item-lister-header-spacer :storage.media-browser
                                                                        {:width "108px"}]
                                      [common/item-lister-header-cell   :storage.media-browser
                                                                        {:label :name          :order-by-key :name :stretch? true}]
                                      [common/item-lister-header-cell   :storage.media-browser
                                                                        {:label :size          :order-by-key :size        :width "160px"}]
                                      [common/item-lister-header-cell   :storage.media-browser
                                                                        {:label :last-modified :order-by-key :modified-at :width "160px"}]
                                      [common/item-lister-header-spacer :storage.media-browser
                                                                        {:width "36px"}]]
                              :control-bar [control-bar]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [search-block]
       [elements/horizontal-separator {:size :xxl}]
       [:div {:style {:display :flex :flex-direction :column-reverse}}
             [:div {:style {:width "100%"}}
                   [media-browser]]
             [media-browser-header]]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
