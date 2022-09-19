
(ns app.storage.frontend.media-selector.views
    (:require [app.common.frontend.api          :as common]
              [app.storage.frontend.core.config :as core.config]
              [layouts.popup-a.api              :as popup-a]
              [mid-fruits.io                    :as io]
              [mid-fruits.format                :as format]
              [plugins.item-browser.api         :as item-browser]
              [x.app-components.api             :as components]
              [x.app-core.api                   :as a]
              [x.app-elements.api               :as elements]
              [x.app-media.api                  :as media]))

;; -- Header components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-by-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-selector])
        order-by-options   [:modified-at/ascending :modified-at/descending :alias/ascending :alias/descending]
        on-click           [:item-browser/choose-order-by! :storage.media-selector {:order-by-options order-by-options}]]
       [elements/icon-button ::order-by-icon-button
                             {:disabled?   browser-disabled?
                              :hover-color :highlight
                              :on-click    on-click
                              :preset      :order-by}]))

(defn upload-files-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-selector])]
       [elements/icon-button ::upload-files-icon-button
                             {:disabled? browser-disabled?
                              :hover-color :highlight
                              :on-click  [:storage.media-selector/upload-files!]
                              :icon      :upload_file}]))

(defn create-folder-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-selector])]
       [elements/icon-button ::create-folder-icon-button
                             {:disabled? browser-disabled?
                              :hover-color :highlight
                              :on-click  [:storage.media-selector/create-directory!]
                              :icon      :create_new_folder}]))

(defn go-home-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-selector])
        at-home?          @(a/subscribe [:item-browser/at-home?          :storage.media-selector])
        error-mode?       @(a/subscribe [:item-browser/get-meta-item     :storage.media-selector :error-mode?])]
       [elements/icon-button ::go-home-icon-button
                             {:disabled? (or browser-disabled? (and at-home? (not error-mode?)))
                              :hover-color :highlight
                              :on-click  [:item-browser/go-home! :storage.media-selector]
                              :preset    :home}]))

(defn go-up-icon-button
  []
  (let [browser-disabled? @(a/subscribe [:item-browser/browser-disabled? :storage.media-selector])
        at-home?          @(a/subscribe [:item-browser/at-home?          :storage.media-selector])]
       [elements/icon-button ::go-up-icon-button
                             {:disabled? (or browser-disabled? at-home?)
                              :hover-color :highlight
                              :on-click  [:item-browser/go-up! :storage.media-selector]
                              :preset    :back}]))

(defn search-items-field
  []
  (let [search-event [:item-browser/search-items! :storage.media-selector {:search-keys [:alias]}]]
       [elements/search-field ::search-items-field
                              {:autoclear?    true
                               :autofocus?    true
                               :emptiable?    true
                               :indent        {:vertical :xxs}
                               :min-width     :s
                               :on-empty      search-event
                               :on-type-ended search-event
                               :placeholder   :search-in-the-directory}]))

(defn control-bar
  []
  [elements/horizontal-polarity ::control-bar
                                {:start-content [:<> [go-up-icon-button]
                                                     [go-home-icon-button]
                                                     [order-by-icon-button]
                                                     [elements/button-separator {:indent {:vertical :xxs}}]
                                                     [create-folder-icon-button]
                                                     [upload-files-icon-button]]
                                 :end-content   [:<> [search-items-field]]}])

(defn header
  [selector-id]
  (let [saving?      @(a/subscribe [:db/get-item [:storage :media-selector/meta-items :saving?]])
        header-label @(a/subscribe [:item-browser/get-current-item-label :storage.media-selector])]
       (if-not saving? [:<> [common/popup-label-bar :storage.media-selector/view
                                                    {:primary-button   {:on-click [:storage.media-selector/save-selected-items!]
                                                                        :label    :save!}
                                                     :secondary-button {:on-click [:ui/close-popup! :storage.media-selector/view]
                                                                        :label    :cancel!}
                                                     :label header-label}]
                            (if-let [first-data-received? @(a/subscribe [:item-browser/first-data-received? :storage.media-selector])]
                                    [control-bar]
                                    [elements/horizontal-separator {:size :xxl}])])))

;; -- Footer components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn footer
  [selector-id]
  (let [saving?             @(a/subscribe [:db/get-item [:storage :media-selector/meta-items :saving?]])
        selected-item-count @(a/subscribe [:storage.media-selector/get-selected-item-count])]
       (if-not saving? [common/item-selector-footer :storage.media-selector
                                                    {:on-discard [:storage.media-selector/discard-selection!]
                                                     :selected-item-count selected-item-count}])))

;; -- Body components ---------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn directory-item-structure
  [browser-id item-dex {:keys [alias size id items modified-at]}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])
        size       (str (-> size io/B->MB format/decimals (str " MB\u00A0\u00A0\u00A0|\u00A0\u00A0\u00A0"))
                        (components/content {:content :n-items :replacements [(count items)]}))]
       [common/list-item-structure browser-id item-dex
                                   {:cells [(let [icon-family (if (empty? items) :material-icons-outlined :material-icons-filled)]
                                                 [common/list-item-thumbnail-icon browser-id item-dex {:icon :folder :icon-family icon-family}])
                                            [common/list-item-primary-cell browser-id item-dex {:label alias :description size :timestamp timestamp :stretch? true}]
                                            [common/list-item-end-icon     browser-id item-dex {:icon :navigate_next}]]}]))

(defn directory-item
  [browser-id item-dex {:keys [id] :as directory-item}]
  [elements/toggle {:content     [directory-item-structure browser-id item-dex directory-item]
                    :hover-color :highlight
                    :on-click    [:item-browser/browse-item! :storage.media-selector id]}])

(defn file-item-structure
  [browser-id item-dex {:keys [alias id modified-at filename size] :as file-item}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])
        size       (-> size io/B->MB format/decimals (str " MB"))]
       [common/list-item-structure browser-id item-dex
                                   {:cells [(if (io/filename->image? alias)
                                                (let [thumbnail (media/filename->media-thumbnail-uri filename)]
                                                     [common/list-item-thumbnail browser-id item-dex {:thumbnail thumbnail}])
                                                [common/list-item-thumbnail-icon browser-id item-dex {:icon :insert_drive_file :icon-family :material-icons-outlined}])
                                            [common/list-item-primary-cell browser-id item-dex {:label alias :description size :timestamp timestamp :stretch? true}]
                                            (if-let [file-selected? @(a/subscribe [:storage.media-selector/file-selected? file-item])]
                                                    [common/list-item-end-icon browser-id item-dex {:icon :check_circle_outline}]
                                                    [common/list-item-end-icon browser-id item-dex {:icon :radio_button_unchecked}])]}]))

(defn file-item
  [browser-id item-dex file-item]
  (let [file-selectable? @(a/subscribe [:storage.media-selector/file-selectable? file-item])]
       [elements/toggle {:content     [file-item-structure browser-id item-dex file-item]
                         :disabled?   (not file-selectable?)
                         :hover-color :highlight
                         :on-click    [:storage.media-selector/file-clicked file-item]}]))

(defn media-item
  [browser-id item-dex {:keys [mime-type] :as media-item}]
  (case mime-type "storage/directory" [directory-item browser-id item-dex media-item]
                                      [file-item      browser-id item-dex media-item]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-lister-ghost-view :storage.media-selector
                                 {:padding "0 24px"}])

(defn- saving-indicator
  []
  (let [selected-item-count @(a/subscribe [:storage.media-selector/get-selected-item-count])
        saving-label         (components/content {:content :n-items-selected :replacements [selected-item-count]})]
       [common/popup-progress-indicator :storage.media-selector/view
                                        {:label saving-label}]))

(defn- media-browser
  []
  [item-browser/body :storage.media-selector
                     {:default-item-id  core.config/ROOT-DIRECTORY-ID
                      :default-order-by :modified-at/descending
                      :ghost-element    #'ghost-view
                      :item-path        [:storage :media-selector/browsed-item]
                      :items-key        :items
                      :items-path       [:storage :media-selector/downloaded-items]
                      :label-key        :alias
                      :list-element     #'media-item
                      :path-key         :path}])

(defn body
  [selector-id]
  (if-let [saving? @(a/subscribe [:db/get-item [:storage :media-selector/meta-items :saving?]])]
          [saving-indicator]
          [:<> [elements/horizontal-separator {:size :m}]
               [media-browser]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  [selector-id]
  [popup-a/layout :storage.media-selector/view
                  {:body                [body   selector-id]
                   :header              [header selector-id]
                   :footer              [footer selector-id]
                   :min-width           :m
                   :stretch-orientation :vertical}])
