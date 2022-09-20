
(ns app.pages.frontend.lister.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- page-item-structure
  [lister-id item-dex {:keys [body modified-at name]}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail-icon lister-id item-dex {:icon :article :icon-family :material-icons-outlined}]
                                            [common/list-item-primary-cell   lister-id item-dex {:label name :stretch? true
                                                                                                 :placeholder :unnamed-page
                                                                                                 :description body}]
                                            [common/list-item-detail         lister-id item-dex {:page timestamp :width "160px"}]
                                            [common/list-item-end-icon       lister-id item-dex {:icon :navigate_next}]]}]))

(defn- page-item
  [lister-id item-dex {:keys [id] :as page-item}]
  [elements/toggle {:page        [page-item-structure lister-id item-dex page-item]
                    :hover-color :highlight
                    :on-click    [:router/go-to! (str "/@app-home/pages/"id)]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-lister-ghost-view :pages.page-lister
                                 {:padding "0 12px"}])

(defn- page-lister
  []
  [item-lister/body :pages.page-lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:pages :page-lister/downloaded-items]
                     :ghost-element    #'ghost-view
                     :list-element     #'page-item}])

(defn- header
  []
  [common/item-lister-header :pages.page-lister
                             {:cells [[common/item-lister-header-spacer :pages.page-lister
                                                                        {:width "108px"}]
                                      [common/item-lister-header-cell   :pages.page-lister
                                                                        {:label :name          :order-by-key :name :stretch? true}]
                                      [common/item-lister-header-cell   :pages.page-lister
                                                                        {:label :last-modified :order-by-key :modified-at :width "160px"}]
                                      [common/item-lister-header-spacer :pages.page-lister
                                                                        {:width "36px"}]]}])

(defn- wrapper
  []
  [common/item-lister-wrapper :pages.page-lister
                              {:item-list        #'page-lister
                               :item-list-header #'header}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- search-block
  []
  [common/item-lister-search-block :pages.page-lister
                                   {:field-placeholder :search-in-pages}])

(defn- breadcrumbs
  []
  [common/item-lister-breadcrumbs :pages.page-lister
                                  {:crumbs [{:label :app-home
                                             :route "/@app-home"}
                                            {:label :pages}]}])

(defn- label-bar
  []
  [common/item-lister-label-bar :pages.page-lister
                                {:create-item-uri "/@app-home/pages/create"
                                 :label           :pages}])

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
                    {:page #'view-structure}])
