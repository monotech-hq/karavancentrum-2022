
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

(defn- content-lister-body
  []
  [item-lister/body :contents.content-lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:contents :content-lister/downloaded-items]
                     :ghost-element    #'common/item-lister-body-ghost-view
                     :list-element     #'content-item}])

(defn- content-lister-header
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :contents.content-lister])
        create-content-uri (str "/@app-home/contents/create")]
       [common/item-lister-create-item-button :contents.content-lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-content-uri}]))

(defn- search-block
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :contents.content-lister])]
       [common/item-lister-search-block :contents.content-lister
                                        {:disabled?         lister-disabled?
                                         :field-placeholder :search-in-contents}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :contents.content-lister])]
       [common/surface-breadcrumbs :contents.content-lister/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :contents}]
                                    :disabled? lister-disabled?}]))

(defn- label-bar
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :contents.content-lister])]
       [common/surface-label :contents.content-lister/view
                             {:disabled? lister-disabled?
                              :label     :contents}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  [common/item-lister-download-info :contents.content-lister {}])

(defn- body
  []
  [:div {:style {:display :flex :flex-direction :column-reverse}}
        [:div {:style {:width "100%"}}
              [content-lister-body]]
        [content-lister-header]])

(defn- header
  []
  (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :contents.content-lister])]
          [:<> [:div {:style {:display :flex :justify-content :space-between :flex-wrap :wrap}}
                     [label-bar]
                     [create-item-button]]
               [breadcrumbs]
               [search-block]]
          [common/item-lister-header-ghost-view :contents.content-lister {}]))

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
