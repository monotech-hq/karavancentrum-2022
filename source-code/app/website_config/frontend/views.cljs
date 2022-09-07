
(ns app.website-config.frontend.views
    (:require [app.storage.frontend.api]
              [app.common.frontend.api :as common]
              [forms.api               :as forms]
              [layouts.surface-a.api   :as surface-a]
              [mid-fruits.css          :as css]
              [mid-fruits.vector       :as vector]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- company-logo-label
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/label ::company-logo-label
                       {:content :logo
                        :disabled? synchronizing?
                        :indent {:left :xs :top :xxl}}]))

(defn- company-logo-thumbnail
  []
  (let [logo-uri @(a/subscribe [:db/get-item [:website-config :config-handler/edited-item :company-logo]])
        synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/thumbnail ::company-logo-thumbnail
                           {:border-radius :m
                            :disabled? synchronizing?
                            :indent {:left :xs}
                            :height        :xxl
                            :width         :xxl
                            :uri (str logo-uri)
                            :on-click [:storage.media-selector/load-selector! :website-config/logo-selector
                                                                              {:value-path [:website-config :config-handler/edited-item :company-logo]}]}]))

(defn- company-logo
  []
  [:<> [company-logo-label]
       [company-logo-thumbnail]])

(defn- company-slogan-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field ::company-slogan-field
                            {:disabled?  synchronizing?
                             :indent     {:top :xs :vertical :xs}
                             :label      :slogan
                             :min-width  :xs
                             :value-path [:website-config :config-handler/edited-item :company-slogan]}]))

(defn- company-name-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field ::company-name-field
                            {:autofocus? true
                             :disabled?  synchronizing?
                             :indent     {:top :xxl :vertical :xs}
                             :label      :company-name
                             :min-width  :xs
                             :value-path [:website-config :config-handler/edited-item :company-name]}]))

(defn- company-info
  []
  [:div (forms/form-row-attributes)
        [:div (forms/form-block-attributes {:ratio 100})
              [company-logo]
              [company-name-field]
              [company-slogan-field]]])

(defn- basic-info
  []
  [company-info])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- delete-group-label-icon-button
  [group-dex _]
  [elements/icon-button {:icon        :delete
                         :icon-family :material-icons-outlined
                         :color       :warning
                         :indent      {:right :xxs :top :xxs}
                         :on-click    [:db/apply-item! [:website-config :config-handler/edited-item :contact-groups]
                                                       vector/remove-nth-item group-dex]}])

(defn- contact-group-label-field
  [group-dex _]
  [elements/text-field {:autofocus?  true
                        :label       :label
                        :indent      {:all :xs}
                        :placeholder ""
                        :value-path  [:website-config :config-handler/edited-item :contact-groups group-dex :label]}])

(defn- email-addresses-field
  [group-dex _]
  [elements/multi-field {:label :email-address
                         :indent {:all :xs}
                         :placeholder "email@domain.com"
                         :value-path [:website-config :config-handler/edited-item :contact-groups group-dex :email-addresses]}])

(defn- phone-numbers-field
  [group-dex _]
  [elements/multi-field {:label :phone-number
                         :indent {:all :xs}
                         :placeholder "+3630 123 4567"
                         :value-path [:website-config :config-handler/edited-item :contact-groups group-dex :phone-numbers]}])

(defn- contact-group
  [group-dex group-props]
  [:<> [elements/horizontal-separator {:size :xxl}]
       [:div {:style {:background-color (css/var "background-color-highlight")
                      :border-radius    (css/var "border-radius-m")
                      :margin "0 12px"}}
             [:div (forms/form-row-attributes)
                   [:div {:style {:flex-grow 1}}
                         [contact-group-label-field group-dex group-props]]
                   [:div {}
                         [delete-group-label-icon-button group-dex group-props]]]
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 50})
                         [phone-numbers-field group-dex group-props]]
                   [:div (forms/form-block-attributes {:ratio 50})
                         [email-addresses-field group-dex group-props]]]]])

(defn- contact-groups
  []
  [:div ;{:style {:display "flex" :flex-direction "column" :grid-row-gap "48px"}}
        (letfn [(f [%1 %2 %3] (conj %1 [contact-group %2 %3]))]
               (let [contact-groups @(a/subscribe [:db/get-item [:website-config :config-handler/edited-item :contact-groups]])]
                    (reduce-kv f [:<>] contact-groups)))])

(defn add-contact-group-button
  []
  [elements/button ::add-contact-group-button
                   {:color         :primary
                    :font-size     :xs
                    :icon          :add
                    :icon-position :right
                    :indent        {:right :xs :top :xxs}
                    :label         :add-contacts-data!
                    :on-click      [:db/apply-item! [:website-config :config-handler/edited-item :contact-groups] vector/cons-item {}]}])

(defn add-contact-group-bar
  []
  [elements/row ::add-contact-group-bar
                {:content [add-contact-group-button]
                 :horizontal-align :right}])

(defn- contacts-data
  []
  [:<> [add-contact-group-bar]
       [contact-groups]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- facebook-links-field
  []
  [elements/multi-field ::facebook-links-field
                        {:autofocus? true
                         :indent     {:vertical :xs :top :xxl}
                         :label      :facebook-link
                         :placeholder "facebook.com/example"
                         :value-path [:website-config :config-handler/edited-item :facebook-links]}])

(defn- instagram-links-field
  []
  [elements/multi-field ::instagram-links-field
                        {:indent     {:vertical :xs :top :xxl}
                         :label      :instagram-link
                         :placeholder "instagram.com/example"
                         :value-path [:website-config :config-handler/edited-item :instagram-links]}])

(defn- youtube-links-field
  []
  [elements/multi-field ::youtube-links-field
                        {:indent     {:vertical :xs :top :xxl}
                         :label      :youtube-link
                         :placeholder "youtube.com/channel/example"
                         :value-path [:website-config :config-handler/edited-item :youtube-links]}])

(defn- social-media
  []
  [:<> [facebook-links-field]
       [instagram-links-field]
       [youtube-links-field]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- meta-description-field
  []
  [elements/multiline-field ::meta-description-field
                            {:label :meta-description
                             :indent {:vertical :xs :top :xxl}
                             :value-path [:website-config :config-handler/edited-item :meta-description]}])

(defn- meta-keywords-field
  []
  [elements/multi-combo-box ::meta-keywords-field
                            {:autofocus? true
                             :label      :meta-keywords
                             :indent     {:vertical :xs :top :xxl}
                             :value-path [:website-config :config-handler/edited-item :meta-keywords]}])

(defn- meta-details
  []
  [:<> [meta-keywords-field]
       [meta-description-field]])

(defn- seo
  []
  [meta-details])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [:<> [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
       [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
       [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
       [elements/ghost {           :indent {:top :xs :vertical :xs} :style {:height "96px"}}]])

(defn- view-selector
  []
  (if-let [data-received? @(a/subscribe [:website-config/data-received?])]
          (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-config])]
               (case current-view-id :basic-info    [basic-info]
                                     :contacts-data [contacts-data]
                                     :social-media  [social-media]
                                     :seo           [seo]))
          [ghost-view]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar-item
  [{:keys [change-keys label view-id]}]
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-config])
        form-changed?   @(a/subscribe [:website-config/form-changed? change-keys])]
       {:active?     (= current-view-id view-id)
        :badge-color (if form-changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! :website-config view-id]}))

(defn- menu-bar
  []
  (if-let [data-received? @(a/subscribe [:website-config/data-received?])]
          [:<> [elements/menu-bar ::menu-bar
                                  {:menu-items [(menu-bar-item {:change-keys [:company-name :company-slogan :company-logo]
                                                                :label   :basic-info
                                                                :view-id :basic-info})
                                                (menu-bar-item {:change-keys [:contact-groups]
                                                                :label   :contacts-data
                                                                :view-id :contacts-data})
                                                (menu-bar-item {:change-keys [:facebook-links :instagram-links :youtube-links]
                                                                :label   :social-media
                                                                :view-id :social-media})
                                                (menu-bar-item {:change-keys [:meta-keywords :meta-description]
                                                                :label   :seo
                                                                :view-id :seo})]}]
               [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- revert-icon-button
  []
  (let [config-changed? @(a/subscribe [:website-config/config-changed?])]
       [elements/icon-button ::revert-icon-button
                             {:disabled?   (not config-changed?)
                              :hover-color :highlight
                              :icon        :settings_backup_restore
                              :on-click    [:website-config/revert-changes!]}]))

(defn- save-icon-button
  []
  (let [config-changed? @(a/subscribe [:website-config/config-changed?])]
       [elements/icon-button ::save-icon-button
                             {:disabled?   (not config-changed?)
                              :hover-color :highlight
                              :icon        :save
                              :color       :primary
                              :on-click    [:website-config/save-changes!]}]))

(defn- controls
  []
  (if-let [data-received? @(a/subscribe [:website-config/data-received?])]
          [:<> [revert-icon-button]
               [save-icon-button]]))

(defn- label
  []
  (let [data-received? @(a/subscribe [:website-config/data-received?])]
       [common/surface-label :webwebsite-config/view
                             {:loading? (not data-received?)
                              :label    :website-config}]))

(defn- label-bar
  []
  [elements/horizontal-polarity ::label-bar
                                {:start-content [label]
                                 :end-content   [controls]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [elements/horizontal-separator {:size :xxl}]
       [label-bar]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]
       [view-selector]
       [elements/horizontal-separator {:size :xxl}]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
