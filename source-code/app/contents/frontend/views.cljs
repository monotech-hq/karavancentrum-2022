
(ns app.contents.frontend.views
  (:require
    [reagent.core :refer [atom]]
    [x.app-core.api       :as a]
    [x.app-elements.api   :as elements]

    [forms.api         :as forms]
    [layouts.surface-a.api     :as surface-a]

    [plugins.api :as plugins]
    [app.common.frontend.api :as common]
    [app.components.frontend.api :as comp]
    [app.contents.frontend.tabs.api :as tabs]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn- revert-icon-button [changed?]
  [elements/icon-button ::revert-icon-button
                        {:disabled?   (not changed?)
                         :hover-color :highlight
                         :icon        :settings_backup_restore
                         :on-click    [:contents/revert-changes!]}])

(defn- save-button []
  [elements/icon-button ::save-icon-button
   {;:disabled?   (not changed?)
    :hover-color :highlight
    :icon        :save
    :color       :primary
    :on-click    [:contents/save!]}])

(defn- controls []
  ; (if-let [loaded?  @(a/subscribe [:contents/loaded?])]
    (let [changed? @(a/subscribe [:contents/changed?])]
      [:<>
        [revert-icon-button changed?]
        [save-button changed?]]))

(defn label [surface-id]
  [common/surface-label surface-id {:label :contents}])

(defn header [surface-id]
  [elements/horizontal-polarity ::label-bar
                                {:start-content [label]
                                 :end-content   [controls]}])

(defn- breadcrumbs []
  (let [loaded? @(a/subscribe [:contents/loaded?])]
       [common/surface-breadcrumbs :contents/view
                                   {:crumbs [{:label :app-home
                                              :route "/@app-home"}
                                             {:label :contents}]
                                    :loading? (not loaded?)}]))

(defn rent-information-tab []
  (let [path [:contents :config-handler/edited-item :rent-informations]]
    [:<>
     [plugins/text-editor {:value-path path}]]))

(defn about-us-tab []
  (let [path [:contents :config-handler/edited-item :about-us]]
    [:<>
     [plugins/text-editor {:value-path path}]]))

(defn main-page-tab []
  (let [path [:contents :config-handler/edited-item :main-page]]
    [:<>
     [plugins/text-editor {:value-path path}]]))

(defn contents [surface-id]
  [:div
   [header surface-id]
   [breadcrumbs]
   [elements/horizontal-separator {:size :xxl}]
   [comp/tabs {:view-id :contents.editor}
    :main-page         [tabs/main-page]
    :rent-informations [tabs/rent-information]
    :about-us          [tabs/about-us]
    :brands            [tabs/brands]]])
   ; [plugins/text-editor {:value-path [:test-editor]}]])

(defn view [surface-id]
  [surface-a/layout surface-id {:content [contents surface-id]}])

;; ---- Components ----
;; -----------------------------------------------------------------------------
