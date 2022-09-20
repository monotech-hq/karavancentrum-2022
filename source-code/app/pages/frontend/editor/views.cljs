
(ns app.pages.frontend.editor.views
    (:require [app.common.frontend.api  :as common]
              [app.storage.frontend.api :as storage]
              [forms.api                :as forms]
              [layouts.surface-a.api    :as surface-a]
              [plugins.item-editor.api  :as item-editor]
              [plugins.text-editor.api  :as text-editor]
              [x.app-core.api           :as a]
              [x.app-elements.api       :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-editor-ghost-view :pages.page-editor
                                 {:padding "0 12px"}])

(defn- menu-bar
  []
  [common/item-editor-menu-bar :pages.page-editor
                               {:menu-items []}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :pages.page-editor])]
       [:<> [menu-bar]]))

(defn- page-editor
  []
  [item-editor/body :pages.page-editor
                    {:auto-title?      true
                     :form-element     #'view-selector
                     :ghost-element    #'ghost-view
                     :initial-item     {:visibility :public}
                     :item-path        [:pages :page-editor/edited-item]
                     :label-key        :name
                     :suggestion-keys  [:name]
                     :suggestions-path [:pages :page-editor/suggestions]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [page-name @(a/subscribe [:db/get-item [:pages :page-editor/edited-item :name]])
        page-id   @(a/subscribe [:router/get-current-route-path-param :item-id])]
       [common/item-editor-breadcrumbs :pages.page-editor
                                       {:crumbs (if page-id [{:label :app-home
                                                              :route "/@app-home"}
                                                             {:label :pages
                                                              :route "/@app-home/pages"}
                                                             {:label       page-name
                                                              :placeholder :unnamed-page
                                                              :route       (str "/@app-home/pages/" page-id)}
                                                             {:label :edit!}]
                                                            [{:label :app-home
                                                              :route "/@app-home"}
                                                             {:label :pages
                                                              :route "/@app-home/pages"}
                                                             {:label :add!}])}]))

(defn- label-bar
  []
  (let [page-name @(a/subscribe [:db/get-item [:pages :page-editor/edited-item :name]])]
       [common/item-editor-label-bar :pages.page-editor
                                     {:label       page-name
                                      :placeholder :unnamed-page}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [page-editor]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:page #'view-structure}])
