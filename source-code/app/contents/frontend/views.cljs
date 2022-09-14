
(ns app.contents.frontend.views
  (:require
    [x.app-core.api       :as a]
    [x.app-elements.api   :as elements]

    [forms.api         :as forms]
    [layouts.surface-a.api     :as surface-a]

    [plugins.api :as plugins]
    [app.common.frontend.api :as common]
    [app.components.frontend.api :as comp]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn- revert-icon-button [changed?]
  [elements/icon-button ::revert-icon-button
                        {:disabled?   (not changed?)
                         :hover-color :highlight
                         :icon        :settings_backup_restore
                         :on-click    [:contents/revert-changes!]}])

(defn- save-icon-button [changed?]
  [elements/icon-button ::save-icon-button
                        {:disabled?   (not changed?)
                         :hover-color :highlight
                         :icon        :save
                         :color       :primary
                         :on-click    [:contents/save!]}])

(defn- controls []
  ; (if-let [loaded?  @(a/subscribe [:contents/loaded?])]
    (let [changed? @(a/subscribe [:contents/changed?])]
      [:div
        [:p (str changed?)] 
       [revert-icon-button changed?]
       [save-icon-button changed?]]))

(defn label [surface-id]
  [common/surface-label surface-id {:label :contents}])

(defn rent-information-tab []
  [plugins/text-editor {:value-path [:contents :config-handler/edited-item :rent-informations]}])

(defn about-us-tab []
  [plugins/text-editor {:value-path [:contents :config-handler/edited-item :about-us]}])

(defn main-page-tab []
  [plugins/text-editor {:value-path [:contents :config-handler/edited-item :main-page]}])

(defn contents [surface-id]
  [:div
   [label surface-id]
   [controls]
   [comp/tabs {:view-id :contents.editor}
    :main-page         [main-page-tab]
    :rent-informations [rent-information-tab]
    :about-us          [about-us-tab]]])
   ; [plugins/text-editor {:value-path [:test-editor]}]])

(defn view [surface-id]
  [surface-a/layout surface-id {:content [contents surface-id]}])

;; ---- Components ----
;; -----------------------------------------------------------------------------
