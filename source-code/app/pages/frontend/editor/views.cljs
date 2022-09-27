
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

(defn- menu-bar
  []
  [common/item-editor-menu-bar :pages.page-editor
                               {:menu-items []}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :pages.page-editor])]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :pages.page-editor])]
       [common/item-editor-control-bar :pages.page-editor
                                       {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :pages.page-editor])
        page-name        @(a/subscribe [:db/get-item [:pages :page-editor/edited-item :name]])
        page-id          @(a/subscribe [:router/get-current-route-path-param :item-id])
        page-uri          (str "/@app-home/pages/" page-id)]
       [common/surface-breadcrumbs :pages.page-editor/view
                                   {:crumbs (if page-id [{:label :app-home :route "/@app-home"}
                                                         {:label :pages    :route "/@app-home/pages"}
                                                         {:label page-name :route page-uri :placeholder :unnamed-page}
                                                         {:label :edit!}]
                                                        [{:label :app-home :route "/@app-home"}
                                                         {:label :pages    :route "/@app-home/pages"}
                                                         {:label :add!}])
                                    :disabled? editor-disabled?}]))

(defn- label-bar
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :pages.page-editor])
        page-name        @(a/subscribe [:db/get-item [:pages :page-editor/edited-item :name]])]
       [common/surface-label :pages.page-editor/view
                             {:disabled?   editor-disabled?
                              :label       page-name
                              :placeholder :unnamed-page}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- page-editor
  []
  [item-editor/body :pages.page-editor
                    {:auto-title?      true
                     :form-element     #'view-structure
                     :ghost-element    #'common/item-editor-ghost-view
                     :initial-item     {:visibility :public}
                     :item-path        [:pages :page-editor/edited-item]
                     :label-key        :name
                     :suggestion-keys  [:name]
                     :suggestions-path [:pages :page-editor/suggestions]}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:page #'page-editor}])
