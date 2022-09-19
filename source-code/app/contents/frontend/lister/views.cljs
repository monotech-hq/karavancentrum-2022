
(ns app.contents.frontend.lister.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-item-structure
  [lister-id item-dex {:keys [body modified-at name]}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail-icon lister-id item-dex {:icon :article :icon-family :material-icons-outlined}]
                                            [common/list-item-primary-cell   lister-id item-dex {:label name :stretch? true
                                                                                                 :placeholder :unnamed-content
                                                                                                 :description body}]
                                            [common/list-item-detail         lister-id item-dex {:content timestamp :width "160px"}]
                                            [common/list-item-end-icon       lister-id item-dex {:icon    :navigate_next}]]}]))

(defn- content-item
  [lister-id item-dex {:keys [id] :as content-item}]
  [elements/toggle {:content     [content-item-structure lister-id item-dex content-item]
                    :hover-color :highlight
                    :on-click    [:router/go-to! (str "/@app-home/contents/"id)]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-lister-ghost-view :contents.content-lister
                                 {:padding "0 12px"}])

(defn- content-lister
  []
  [item-lister/body :contents.content-lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:contents :content-lister/downloaded-items]
                     :ghost-element    #'ghost-view
                     :list-element     #'content-item}])

(defn- header
  []
  [common/item-lister-header :contents.content-lister
                             {:cells [[common/item-lister-header-spacer :contents.content-lister
                                                                        {:width "108px"}]
                                      [common/item-lister-header-cell   :contents.content-lister
                                                                        {:label :name          :order-by-key :name :stretch? true}]
                                      [common/item-lister-header-cell   :contents.content-lister
                                                                        {:label :last-modified :order-by-key :modified-at :width "160px"}]
                                      [common/item-lister-header-spacer :contents.content-lister
                                                                        {:width "36px"}]]}])

(defn- wrapper
  []
  [common/item-lister-wrapper :contents.content-lister
                              {:item-list        #'content-lister
                               :item-list-header #'header}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- search-block
  []
  [common/item-lister-search-block :contents.content-lister
                                   {:field-placeholder :search-in-contents}])

(defn- breadcrumbs
  []
  [common/item-lister-breadcrumbs :contents.content-lister
                                  {:crumbs [{:label :app-home
                                             :route "/@app-home"}
                                            {:label :contents}]}])

(defn- label-bar
  []
  [common/item-lister-label-bar :contents.content-lister
                                {:create-item-uri "/@app-home/contents/create"
                                 :label           :contents}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [search-block]
       [elements/horizontal-separator {:size :xxl}]
       [wrapper]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
