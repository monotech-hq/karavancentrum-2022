
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

(defn- page-lister-body
  []
  [item-lister/body :pages.page-lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:pages :page-lister/downloaded-items]
                     :ghost-element    #'common/item-lister-body-ghost-view
                     :list-element     #'page-item}])

(defn- page-lister-header
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :pages.page-lister])
        create-page-uri (str "/@app-home/pages/create")]
       [common/item-lister-create-item-button :pages.page-lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-page-uri}]))

(defn- search-block
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :pages.page-lister])]
       [common/item-lister-search-block :pages.page-lister
                                        {:disabled?         lister-disabled?
                                         :field-placeholder :search-in-pages}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :pages.page-lister])]
       [common/surface-breadcrumbs :pages.page-lister/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :pages}]
                                    :disabled? lister-disabled?}]))

(defn- label-bar
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :pages.page-lister])]
       [common/surface-label :pages.page-lister/view
                             {:disabled? lister-disabled?
                              :label     :pages}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  [common/item-lister-download-info :pages.page-lister {}])

(defn- body
  []
  [:div {:style {:display :flex :flex-direction :column-reverse}}
        [:div {:style {:width "100%"}}
              [page-lister-body]]
        [page-lister-header]])

(defn- header
  []
  (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :pages.page-lister])]
          [:<> [:div {:style {:display :flex :justify-content :space-between :flex-wrap :wrap}}
                     [label-bar]
                     [create-item-button]]
               [breadcrumbs]
               [search-block]]
          [common/item-lister-header-ghost-view :pages.page-lister {}]))

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [elements/horizontal-separator {:size :xxl}]
        [body]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [footer]]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
